package com.ruoyi.web.controller.Queue;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.coze.CozeRequestJsonUtils;
import com.ruoyi.system.coze.utils.CozeWorkflowClient;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.service.IPsdTaskService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
            String configString = task.getConfig();
            // 初始化ObjectMapper（推荐作为静态成员）
            ObjectMapper mapper = new ObjectMapper();

            // 配置解析改造
            JsonNode configNode = mapper.readTree(configString);  // [5](@ref)

            // 转换为可修改的ObjectNode

            ObjectNode config = (ObjectNode) configNode;  // [3](@ref)
            List<String> nameList = psdMapper.selectAccountByName(task.getAccountName());
            ArrayNode historyArray = mapper.createArrayNode();
            nameList.forEach(historyArray::add);
            config.set("historyName", historyArray);  // [3,5](@ref)

            // 请求数据
            JsonNode jsonResponse = CozeWorkflowClient.executeWithRetry(config);

            // 将 answer 转换为 JSONObject 对象
            JsonNode rootNode = jsonResponse;
            ObjectNode root = (ObjectNode) rootNode;
            root.set("baseConfig", config.get("baseConfig"));
            String answer = root.toString();

            if (psdMapper.getAutoCheck().equals("0")) {
                String extranetIp = psdMapper.getCheckInfo().getExtranetIp();
                // 人工审核
                String url = "http://" + extranetIp + "/check?uuid=" + task.getUuid();
                // 调试输出
                sendCardNotification("https://open.feishu.cn/open-apis/bot/v2/hook/92db315f-20e9-486e-bd9f-f04f566fe3be",
                        task.getCreateBy(), task.getAccountName() + "-" + task.getTemplateName(), url);
                // 更新状态为审核中
                task.setStatus("3");
            }else {
                // 读取 JSX 模板
                String basePath = System.getProperty("user.dir");
                String jsxTemplatePath = basePath + File.separator + "jsx" + File.separator + "generate.jsx";
                String jsxTemplate = new String(Files.readAllBytes(Paths.get(jsxTemplatePath)), StandardCharsets.UTF_8);
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
                String userName = "(var\\s+userName\\s*=\\s*)[^;]*;";
                String timePattern = "(var\\s+foldersName\\s*=\\s*)[^;]*;";

                String modifiedJsx = jsxTemplate
                        .replaceFirst(configPattern, "var CONFIG = " + answer + ";")
                        .replaceFirst(userName, "$1\"" + task.getCreateBy() + "\";")
                        .replaceFirst(timePattern, "$1\"" + foldersName + "\";");

                System.err.println("替换后的 JSX:\n" + modifiedJsx);

                // 调用 Photoshop
                ActiveXComponent ps = new ActiveXComponent("Photoshop.Application");
                Dispatch.invoke(ps, "DoJavaScript", Dispatch.Method, new Object[]{modifiedJsx}, new int[1]);
                // 更新状态为成功
                task.setStatus("0");
            }

            // 更新当前任务config
            task.setConfig(answer);
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

        } catch (Exception e) {
            // 判断是否为路径不存在的异常
            if (isPathNotFound(e)) {
                throw new RuntimeException("PSD文件路径不存在或无法访问", e); // 手动抛出自定义异常
            }
            System.err.println("JSX 读取失败：" + e.getMessage());
        }finally {
            // 任务失败，更新状态
            if (task.getStatus().equals("2")) {
                task.setStatus("1");
                psdTaskService.updatePsdTask(task);
            }
        }
    }

    private static boolean isPathNotFound(Exception e) {
        // 优先检查消息关键词
        String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
        if (msg.contains("打不开") || msg.contains("不存在") || msg.contains("找不到")) {
            return true;
        }

        // 尝试通过反射获取错误码（Jacob库的ComFailException实际包含private的hresult字段）
        try {
            Field hresultField = e.getClass().getDeclaredField("hresult");
            hresultField.setAccessible(true);
            int code = hresultField.getInt(e);
            return code == 0x80030003 || code == 0x80070002; // 文件不存在的错误码
        } catch (Exception ex) {
            return false; // 反射失败则仅依赖消息判断
        }
    }

    public static void sendCardNotification(String webhookUrl, String reviewer, String templateName, String auditUrl) {
        // 构建卡片消息内容
        JSONObject card = new JSONObject();
        card.put("config", new JSONObject() {{
            put("wide_screen_mode", true);
        }});
        card.put("header", new JSONObject() {{
            put("title", new JSONObject() {{
                put("tag", "plain_text");
                put("content", "图片生成通知");
            }});
        }});
        card.put("elements", JSON.parseArray("[" +
                "  {" +
                "    \"tag\": \"div\"," +
                "    \"text\": { \"tag\": \"lark_md\", \"content\": \"**审核人：** " + reviewer + "\" }" +
                "  }," +
                "  {" +
                "    \"tag\": \"div\"," +
                "    \"text\": { \"tag\": \"lark_md\", \"content\": \"**模板名称：** " + templateName + "\" }" +
                "  }," +
                "  {" +
                "    \"tag\": \"div\"," +
                "    \"text\": { \"tag\": \"lark_md\", \"content\": \"**审核网址：** [点击前往](" + auditUrl + ")\" }" +
                "  }" +
                "]"));

        JSONObject content = new JSONObject();
        content.put("card", card);

        JSONObject payload = new JSONObject();
        payload.put("msg_type", "interactive");
        payload.put("card", card);

        // 发送 POST 请求
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setDoOutput(true);

            String payloadStr = payload.toJSONString();
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payloadStr.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response: " + response.toString());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendCardNotification("https://open.feishu.cn/open-apis/bot/v2/hook/92db315f-20e9-486e-bd9f-f04f566fe3be",
                "admin", "test", "http://localhost/check?uuid=9911e4c5-6bbf-40e5-9d1a-9f142a7d16e5");
    }
}
