package com.ruoyi.web.controller.Queue;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.ruoyi.system.coze.CozeRequestJsonUtils;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.service.IPsdTaskService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class PhotoshopTaskQueue {
    private static final BlockingQueue<PsdTask> TASK_QUEUE = new LinkedBlockingQueue<>();
    private static volatile boolean running = true;
    private static ActiveXComponent ps;  // Photoshop 共享实例

    @Autowired
    private IPsdTaskService psdTaskServiceTemp; // 非静态，Spring 可注入

    @Autowired
    private PSDMapper psdMapperTemp; // 非静态，Spring 可注入

    private static IPsdTaskService psdTaskService;
    private static PSDMapper psdMapper;

    // Spring 初始化后赋值给静态变量
    @PostConstruct
    public void init() {
        psdTaskService = psdTaskServiceTemp;
        psdMapper = psdMapperTemp;
        // 加载未完成任务到队列
        loadPendingTasks();
    }

    private void loadPendingTasks() {
        PsdTask query = new PsdTask();
        query.setStatus("2"); // 状态2表示进行中

        List<PsdTask> pendingTasks = psdTaskService.selectPsdTaskList(query);
        System.out.println("发现"+pendingTasks.size()+"个未完成任务");

        pendingTasks.forEach(task -> {
            // 加入队列
            PhotoshopTaskQueue.addTask(task);
        });
    }

    static {
        // 启动一个单独的线程处理 Photoshop 任务
        new Thread(() -> {
            while (running) {
                try {
                    PsdTask task = TASK_QUEUE.take(); // 阻塞式获取任务
                    processTask(task);  // 执行任务
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "PhotoshopTaskProcessor").start();
    }

    public static void addTask(PsdTask task) {
        TASK_QUEUE.offer(task); // 将任务加入队列
    }

    private static synchronized ActiveXComponent getPhotoshopInstance() {
        if (ps == null) {
            ps = new ActiveXComponent("Photoshop.Application");
        }
        return ps;
    }

    private static void processTask(PsdTask task) {
        try {
            // 读取 JSX 模板
            String basePath = System.getProperty("user.dir");
            String jsxTemplatePath = basePath + File.separator + "jsx" + File.separator + "generate.jsx";
            String jsxTemplate = new String(Files.readAllBytes(Paths.get(jsxTemplatePath)), StandardCharsets.UTF_8);
            String configString = task.getConfig();
            JSONObject config = JSONObject.parseObject(configString);
            List<String> nameList = psdMapper.selectAccountByName(task.getAccountName());
            System.err.println("历史名字：" + nameList);

            JSONArray historyArray = new JSONArray(nameList);
            config.put("historyName", historyArray);

            // 生成配置字符串
            String answer = CozeRequestJsonUtils.test_chat_completions(String.valueOf(config));

            // 将 answer 转换为 JSONObject 对象
            // 使用 Jackson 解析 JSON 字符串
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(answer);
            answer = answer.replaceAll("\\\\", "\\\\\\\\");

            // 替换 JSX 模板
            LocalDateTime time = task.getcreateDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
            String formattedDate = time.format(formatter); // 输出示例：25-03-19
            // 安全转义
            String safeDate = StringEscapeUtils.escapeEcmaScript(formattedDate);
            String foldersName = task.getTemplateName() + "_" + task.getAccountName() + "_" + safeDate;

            // 精准替换
            String configPattern = "var CONFIG = .*?;";
            String timePattern = "(var\\s+foldersName\\s*=\\s*)[^;]*;";

            String modifiedJsx = jsxTemplate
                    .replaceFirst(configPattern, "var CONFIG = " + answer + ";")
                    .replaceFirst(timePattern, "$1\"" + foldersName + "\";");

            // 调试输出
            System.out.println("替换后的 JSX:\n" + modifiedJsx);

            // 调用 Photoshop
            ActiveXComponent ps = new ActiveXComponent("Photoshop.Application");
            Dispatch.invoke(ps, "DoJavaScript", Dispatch.Method, new Object[]{modifiedJsx}, new int[1]);
            // 更新状态为成功
            task.setStatus("0");
            psdTaskService.updatePsdTask(task);

            List<String> sampleTextList = new ArrayList<>();

            // 获取 imageConfigs 数组节点
            JsonNode imageConfigs = root.get("imageConfigs");
            if (imageConfigs != null && imageConfigs.isArray()) {
                for (JsonNode imageConfig : imageConfigs) {
                    // 获取 textLayerConfigs 对象节点
                    JsonNode textLayerConfigs = imageConfig.get("textLayerConfigs");
                    if (textLayerConfigs != null && textLayerConfigs.isObject()) {
                        // 遍历 textLayerConfigs 的每个字段
                        Iterator<Map.Entry<String, JsonNode>> fields = textLayerConfigs.fields();
                        while (fields.hasNext()) {
                            Map.Entry<String, JsonNode> entry = fields.next();
                            JsonNode textLayer = entry.getValue();
                            String name = textLayer.get("name").asText();
                            // 判断 name 中是否包含 "名字"
                            if (name.contains("名字")) {
                                String sampleText = textLayer.get("sampleText").asText();
                                sampleTextList.add(sampleText);
                            }
                        }
                    }
                }
            }

            // 输出结果
            System.err.println("包含 '名字' 的 sampleText 列表: " + sampleTextList);
            if (!sampleTextList.isEmpty()) {
                psdMapper.insertAccountByName(task.getAccountName(), sampleTextList);
            }

        } catch (IOException e) {
            System.out.println("JSX 读取失败：" + e.getMessage());
        }finally {
            // 任务失败，更新状态
            if (task.getStatus().equals("2")) {
                task.setStatus("1");
                psdTaskService.updatePsdTask(task);
            }
        }
    }
}
