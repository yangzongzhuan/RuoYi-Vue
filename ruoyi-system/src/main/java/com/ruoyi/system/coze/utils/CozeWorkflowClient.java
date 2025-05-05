package com.ruoyi.system.coze.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

@Component
public class CozeWorkflowClient {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

    // 全局控制参数
    private static final int MAX_RETRIES = 3;           // 最大异步请求重试次数（每次包含 10 分钟轮询）
    private static final long TOTAL_TIMEOUT_MS = 600_000; // 单次异步请求轮询总超时 10 分钟
    private static final long POLL_INTERVAL_MS = 2_000;   // 轮询间隔 2 秒

    /**
     * 全链路请求入口：
     * 1. 提交异步请求，获取 execute_id
     * 2. 使用该 execute_id 轮询任务状态，最多轮询 4 分钟（每 2 秒轮询一次）
     *    - 若在 4 分钟内获取到 "Success" 状态，则提取 output 返回
     *    - 若任务 "Failed" 或超时，则抛出异常，外层捕获后重新提交异步请求
     * 3. 最多重试 MAX_RETRIES 次异步请求，每次失败后指数退避等待
     *
     * @param parameters 请求参数（JSON）
     * @return 成功时解析后的输出 JSON 数据
     * @throws Exception 超过重试次数或最终失败时抛出异常
     */
    public static JsonNode executeWithRetry(JsonNode parameters, String token) throws Exception {
        int asyncRetryCount = 0;
        Exception lastException = null;

        while (asyncRetryCount < MAX_RETRIES) {
            try {
                // 提交异步请求，获取初始响应和 execute_id
                JsonResponse initResponse = submitAsyncRequest(parameters, token);
                System.err.println("提交异步请求成功：" + initResponse);
                validateResponse(initResponse);
                String executeId = initResponse.getExecuteId();

                // 使用 execute_id 轮询结果（超时 4 分钟，每 2 秒轮询一次）
                JsonNode output = pollResultWithTimeout(executeId, token);
                return output;
            } catch (Exception e) {
                asyncRetryCount++;
                lastException = e;
                System.err.println("异步请求/轮询失败，重试次数: " + asyncRetryCount + "，原因: " + e.getMessage());
                // 指数退避等待（延时：2^n 秒，最多 30 秒）
                long delay = Math.min((long) Math.pow(2, asyncRetryCount) * 1000, 30_000);
                Thread.sleep(delay);
            }
        }
        throw new Exception(String.format("请求失败（已重试 %d 次）", asyncRetryCount), lastException);
    }

    // 提交异步请求，返回包含 execute_id 的响应
    private static JsonResponse submitAsyncRequest(JsonNode parameters, String token) throws IOException {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://api.coze.cn/v1/workflow/run");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(60_000);
            conn.setReadTimeout(60_000);
            conn.setDoOutput(true);

            // 构建请求体，设置 is_async 为 true
            try (OutputStream os = conn.getOutputStream()) {
                JsonNode requestBody = mapper.createObjectNode()
                        .put("workflow_id", "7487134037049835583")
                        .put("is_async", true)
                        .set("parameters", parameters);
                mapper.writeValue(os, requestBody);
            }
            return parseResponse(conn);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    /**
     * 轮询请求：
     * 在单次异步请求中，使用 execute_id 轮询任务状态，每 POLL_INTERVAL_MS 轮询一次，
     * 若在 TOTAL_TIMEOUT_MS（10 分钟）内获取到 "Success" 状态，则返回解析后的输出；
     * 若任务返回 "Failed" 或轮询超时，则抛出异常。
     */
    public static JsonNode pollResultWithTimeout(String executeId, String token) throws Exception {
        long pollStartTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - pollStartTime) < TOTAL_TIMEOUT_MS) {
            try {
                JsonResponse pollResponse = pollWorkflowResult(executeId, token);
                validateResponse(pollResponse);

                // 轮询返回的数据 data 为数组，取第一个元素
                JsonNode item = pollResponse.getData().get(0);
                String status = item.path("execute_status").asText();
                System.out.println("轮询中。。。。：" + status);

                if ("Success".equals(status)) {
                    return parseNestedOutput(item);
                } else if ("Failed".equals(status)) {
                    throw new Exception("任务执行失败: " + item.path("error_message").asText());
                }
                // 状态为 Running 或其他，则继续轮询
            } catch (Exception e) {
                // 若轮询过程中出现异常，则直接抛出，由外层重试异步请求
                throw new Exception("轮询异常: " + e.getMessage(), e);
            }
            Thread.sleep(POLL_INTERVAL_MS);
        }
        throw new Exception("轮询超时，未在10分钟内获得结果");
    }

    /**
     * 解析嵌套 JSON 数据：
     * 从轮询返回的 item 中提取 output 字段，先处理转义字符，再解析其中 "Output" 字段，并返回其解析结果。
     */
    private static JsonNode parseNestedOutput(JsonNode item) throws IOException {
        System.err.println("轮询成功返回的 item：" + item);

        ObjectMapper mapper = new ObjectMapper();

        // 第一步：解析 output 字符串为 JsonNode
        String outerOutputStr = item.get("output").asText();
        JsonNode outerOutputNode = mapper.readTree(outerOutputStr);

        // 第二步：获取 "Output" 字段（它是字符串），再继续解析
        String innerOutputStr = outerOutputNode.get("Output").asText();
        JsonNode innerOutputNode = mapper.readTree(innerOutputStr);

        return innerOutputNode;
    }

    // 校验响应是否成功：若 code 不为 0，则认为响应异常
    private static void validateResponse(JsonResponse response) throws Exception {
        if (response.getCode() != 0) {
            throw new Exception("服务端返回错误码: " + response.getCode());
        }
    }

    // 轮询请求，调用 GET 接口，传入 executeId
    private static JsonResponse pollWorkflowResult(String executeId, String token) throws IOException {
        String url = String.format("https://api.coze.cn/v1/workflows/%s/run_histories/%s",
                "7487134037049835583", executeId);
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", token);
        conn.setConnectTimeout(30_000);
        conn.setReadTimeout(30_000);
        return parseResponse(conn);
    }

    // 解析 HTTP 响应，封装成 JsonResponse 对象
    private static JsonResponse parseResponse(HttpURLConnection conn) throws IOException {
        try (InputStream is = conn.getResponseCode() == 200 ? conn.getInputStream() : conn.getErrorStream()) {
            JsonNode root = mapper.readTree(is);
            // 尝试从返回 JSON 的根级别提取 execute_id（若存在）
            String executeId = root.has("execute_id") ? root.path("execute_id").asText() : "";
            return new JsonResponse(root.path("code").asInt(), root.path("data"), executeId);
        }
    }

    // 响应包装类
    public static class JsonResponse {
        private final int code;
        private final JsonNode data;
        private final String executeId;

        public JsonResponse(int code, JsonNode data, String executeId) {
            this.code = code;
            this.data = data;
            this.executeId = executeId;
        }

        public int getCode() {
            return code;
        }
        public JsonNode getData() {
            return data;
        }
        public String getExecuteId() {
            return executeId;
        }

        @Override
        public String toString() {
            return String.format("{code: %d, data: %s, executeId: %s}", code, data, executeId);
        }
    }
}
