package com.ruoyi.web.controller.Queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.ruoyi.common.utils.JYFileUploadUtil;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.mapper.PsdTaskMapper;
import com.ruoyi.web.controller.util.qiniuyun.QiNiuYunUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * @author 11254$
 * @packageName:$
 * @class:$
 * @date 2025/4/15$
 */
@Component
public class TemPhotoshopJsxQuenu {

    private static final BlockingQueue<PsdTask> TASK_QUEUE = new LinkedBlockingQueue<>();
    private static volatile boolean running = true;

    private static ActiveXComponent ps;  // Photoshop 共享实例

    @Autowired
    private PsdTaskMapper psdTaskMapperTemp;

    private static PsdTaskMapper psdTaskMapper;
    @PostConstruct
    public void init() {
        psdTaskMapper = psdTaskMapperTemp;
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
            String basePath = System.getProperty("user.dir");
            String jsxTemplatePath = basePath + File.separator + "jsx" + File.separator + "generate.jsx";
            String jsxTemplate = new String(Files.readAllBytes(Paths.get(jsxTemplatePath)), StandardCharsets.UTF_8);
            String answer = task.getConfig();
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

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String datePath = today.format(formatter1);
            // 初始化ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode config = (ObjectNode) mapper.readTree(task.getConfig());
            String path = config.path("baseConfig").path("imageSavePath").asText();
            String realPath = path + "\\" + datePath + "\\" + task.getCreateBy() + "\\" + foldersName;
            task.setRealPath(realPath);
            File outputDir = new File(realPath);
            File[] images = outputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));

            if (images == null || images.length == 0) {
                System.out.println("目录中无 JPG 文件，跳过上传。");
                throw new RuntimeException("目录中无 JPG 文件");
            }

            File urlFile = new File(outputDir, "url.txt");

            try (
                    FileWriter fw = new FileWriter(urlFile, true);
                    BufferedWriter writer = new BufferedWriter(fw)
            ) {

                for (File img : images) {
                    if (img.getName().contains("封面")) {
                        // 不上传标题
                        continue;
                    }
                    // 调用上传工具，返回图片 URL
                    String imageUrl = QiNiuYunUtil.uploadFile(img);

                    // 追加写入 url.txt，并换行
                    writer.write(imageUrl);
                    writer.newLine();  // BufferedWriter.newLine()

                    System.out.println("上传成功: " + img.getName() + " → " + imageUrl);
                }
            } catch (IOException e) {
                System.err.println("无法打开或写入 url.txt: " + urlFile.getAbsolutePath());
                e.printStackTrace();
            }

            System.out.println("所有图片处理完成，URL 已追加到：" + urlFile.getAbsolutePath());


            task.setStatus("0"); // 更新为成功
            psdTaskMapper.updatePsdTask(task);

        } catch (Exception e) {
            // 任务失败，更新状态
            task.setStatus("1");
            psdTaskMapper.updatePsdTask(task);
            System.err.println("JSX 执行失败：" + e.getMessage());
        }
    }
}
