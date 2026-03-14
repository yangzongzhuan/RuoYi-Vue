package com.ruoyi.web.controller.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.system.domain.Copywriting;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.mapper.PsdTaskMapper;
import com.ruoyi.system.service.ICopywritingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抖音自动发布定时任务
 * 每10秒检测一次任务状态，如果任务成功且设置了自动发布，则自动发布到抖音
 */
@RestController
@RequestMapping("/psd/task")
public class DouyinAutoPublishTask {

    private static final Logger logger = LoggerFactory.getLogger(DouyinAutoPublishTask.class);

    @Autowired
    private PsdTaskMapper psdTaskMapper;

    @Value("${server.port}")
    private int serverPort;

    private static final String PYTHON_SERVICE_URL = "http://localhost:5409";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // 防止重复发送的锁集合（任务ID -> 处理中标识）
    private static final Set<Long> processingTasks = ConcurrentHashMap.newKeySet();

    @Autowired
    private ICopywritingService copywritingService;

    /**
     * 定时任务：每60秒检查一次需要自动发布的任务
     */
    @Scheduled(fixedRate = 60000)
    public void checkAndPublishAutoTasks() {
        try {
            // 查询需要发布的任务
            List<PsdTask> tasks = psdTaskMapper.selectAutoPublishTasks();

            if (tasks == null || tasks.isEmpty()) {
                return;
            }

            logger.info("找到 {} 个需要自动发布的任务", tasks.size());

            for (PsdTask task : tasks) {
                try {
                    processAutoPublish(task);
                } catch (Exception e) {
                    logger.error("处理任务自动发布失败，任务ID: {}, 错误: {}", task.getId(), e.getMessage(), e);
                }
            }

        } catch (Exception e) {
            logger.error("自动发布定时任务执行异常", e);
        }
    }

    /**
     * 处理单个任务的自动发布
     */
    private void processAutoPublish(PsdTask task) {
        Long taskId = task.getId();

        // 防重复检查
        if (processingTasks.contains(taskId)) {
            logger.warn("任务 {} 正在处理中，跳过", taskId);
            return;
        }

        try {
            // 添加到处理中集合
            processingTasks.add(taskId);

            logger.info("开始自动发布任务 {}, 任务状态: {}, dyStatus: {}",
                taskId, task.getStatus(), task.getDyStatus());

            // 获取抖音账号列表，匹配账号
            String accountFile = matchDouyinAccount(task.getAccountName());
            if (accountFile == null) {
                logger.error("任务 {} 自动发布终止：未找到匹配的抖音账号，accountName={}",
                    taskId, task.getAccountName());
                // 终止自动发布，将 isAutoPush 设置为 0
                task.setIsAutoPush(0);
                psdTaskMapper.updatePsdTask(task);
                return;
            }

            // 获取标题和文案
            Map<String, String> copywriterData = getCopywriterFromTask(task);

            // 确定发布方式
            String publishType = "immediate";
            String publishTime = "";
            if (task.getDyPushTime() != null && !task.getDyPushTime().isEmpty()) {
                publishType = "scheduled";
                publishTime = task.getDyPushTime();
            }

            // 更新状态为发布中
            task.setDyStatus("0");
            psdTaskMapper.updatePsdTask(task);

            // 调用发布接口
            doPublish(task, accountFile, copywriterData, publishType, publishTime);

        } finally {
            // 从处理中集合移除
            processingTasks.remove(taskId);
        }
    }

    /**
     * 匹配抖音账号
     */
    private String matchDouyinAccount(String accountName) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = PYTHON_SERVICE_URL + "/custom/api/douyin/getAccounts";

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                Map<String, Object> dataMap = (Map<String, Object>) body.get("data");
                List<List<Object>> accounts = (List<List<Object>>) dataMap.get("data");

                // 查找匹配的账号
                for (List<Object> account : accounts) {
                    if (account.size() >= 4 && "3".equals(String.valueOf(account.get(1)))) {
                        String name = String.valueOf(account.get(3));
                        String filePath = String.valueOf(account.get(2));
                        if (accountName != null && accountName.equals(name)) {
                            logger.info("匹配到抖音账号: name={}, filePath={}", name, filePath);
                            return filePath;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取抖音账号列表失败", e);
        }
        return null;
    }

    /**
     * 从任务获取标题和文案
     */
    private Map<String, String> getCopywriterFromTask(PsdTask task) {
        Map<String, String> result = new HashMap<>();
        result.put("title", "");
        result.put("copywriter", "");

        try {
            String realPath = task.getRealPath();
            if (realPath == null || realPath.isEmpty()) {
                return result;
            }

            // 构建copywriter.txt文件路径
            String copywriterFilePath = realPath + File.separator + "copywriter.txt";
            File copywriterFile = new File(copywriterFilePath);

            if (!copywriterFile.exists()) {
                return result;
            }

            // 读取文件内容
            String content = new String(Files.readAllBytes(copywriterFile.toPath()), java.nio.charset.StandardCharsets.UTF_8);

            // 如果文件内容是 JSON，优先从 JSON 的 copywriter 字段中取数据
            String copywriterSource = content;
            String trimmedContent = content.trim();
            if (!trimmedContent.isEmpty() && (trimmedContent.startsWith("{") || trimmedContent.startsWith("["))) {
                try {
                    JsonNode jsonNode = OBJECT_MAPPER.readTree(trimmedContent);
                    JsonNode copywriterNode = jsonNode.path("copywriter");
                    if (!copywriterNode.isMissingNode()) {
                        copywriterSource = copywriterNode.asText("");
                    }
                } catch (Exception jsonEx) {
                    logger.warn("copywriter.txt内容为JSON但解析失败，回退为纯文本处理: {}", jsonEx.getMessage());
                }
            }

            // 处理内容，提取标题和文案
            String normalizedCopywriter = copywriterSource
                    .replace("\\r\\n", "\n")
                    .replace("\\\\n", "\n");
            String[] lines = normalizedCopywriter.split("\\r?\\n");

            if (lines.length > 0) {
                // 第一行作为标题，去除#号和空格
                String title = lines[0].replaceFirst("^#+\\s*", "").trim();
                result.put("title", title);

                // 剩余行作为文案内容
                if (lines.length > 1) {
                    String copywriter = Arrays.stream(lines, 1, lines.length)
                            .collect(java.util.stream.Collectors.joining("\n"));
                    result.put("copywriter", copywriter);
                }
            }

        } catch (Exception e) {
            logger.error("获取copywriter内容失败", e);
        }

        return result;
    }

    /**
     * 执行发布操作
     */
    private void doPublish(PsdTask task, String accountFile, Map<String, String> copywriterData,
                          String publishType, String publishTime) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = PYTHON_SERVICE_URL + "/custom/api/douyin/publishImage";

            String callbackUrl = "http://localhost:" + serverPort + "/psd/task/douyinCallback";

            // 使用收藏音乐，musicName 为 musicNum 的值
            String musicType = "fav";
            String musicName = task.getMusicNum() != null ? String.valueOf(task.getMusicNum()) : "1";

            // 从评论表随机获取一条评论内容（如果有评论表的话）
            String comment = "";
            try {
                Copywriting randomComment = copywritingService.selectRandomCopywriting();
                if (randomComment != null && randomComment.getContent() != null) {
                    comment = randomComment.getContent();
                    logger.info("随机获取评论内容: {}", comment);
                }
            } catch (Exception e) {
                logger.warn("获取随机评论失败: {}", e.getMessage());
            }

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("account_file", accountFile);
            requestBody.put("folder_path", task.getRealPath());
            requestBody.put("title", copywriterData.get("title"));
            requestBody.put("copywriter", copywriterData.get("copywriter"));
            requestBody.put("comment", comment);  // 评论内容
            requestBody.put("music_name", musicName);
            requestBody.put("music_type", musicType);
            requestBody.put("publish_type", publishType);
            requestBody.put("publish_time", publishTime);
            requestBody.put("task_id", task.getId().toString());
            requestBody.put("callback_url", callbackUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            logger.info("调用Python服务发布，任务ID: {}, publishType: {}, publishTime: {}, musicType: {}, musicName: {}",
                task.getId(), publishType, publishTime, musicType, musicName);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                Integer code = (Integer) body.get("code");

                if (code != null && code == 200) {
                    logger.info("自动发布任务已提交，任务ID: {}", task.getId());
                } else {
                    // 发布失败，更新状态
                    task.setDyStatus("2");
                    psdTaskMapper.updatePsdTask(task);
                    logger.error("自动发布失败，任务ID: {}, 错误: {}", task.getId(), body.get("msg"));
                }
            } else {
                task.setDyStatus("2");
                psdTaskMapper.updatePsdTask(task);
                logger.error("调用发布接口失败，任务ID: {}", task.getId());
            }

        } catch (Exception e) {
            logger.error("自动发布异常，任务ID: {}", task.getId(), e);
            task.setDyStatus("2");
            psdTaskMapper.updatePsdTask(task);
        }
    }
}
