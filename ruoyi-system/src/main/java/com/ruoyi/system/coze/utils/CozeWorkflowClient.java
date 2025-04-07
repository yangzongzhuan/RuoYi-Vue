package com.ruoyi.system.coze.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class CozeWorkflowClient {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

    public static JsonResponse executeWorkflow(JsonNode parameters) throws IOException {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://api.coze.cn/v1/workflow/run");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer pat_veu2EX4VFvFtDM6hdpIIlGpoqHQOCptF564G4QDmaVvTt6U0onU6Qc8CuYU0Do02");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // 构建请求体（流式写入）
            try (OutputStream os = conn.getOutputStream()) {
                JsonNode requestBody = mapper.createObjectNode()
                        .put("workflow_id", "7487134037049835583")
                        .set("parameters", parameters);
                mapper.writeValue(os, requestBody);
            }

            return parseResponse(conn);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private static JsonResponse parseResponse(HttpURLConnection conn) throws IOException {
        try (InputStream is = conn.getResponseCode() == 200 ? conn.getInputStream() : conn.getErrorStream()) {
            // 流式解析加速
            JsonParser parser = mapper.getFactory().createParser(is);
            JsonNode rootNode = mapper.readTree(parser);

            int code = rootNode.path("code").asInt(-1);
            JsonNode dataNode = rootNode.path("data");

            // 处理双引号包裹的嵌套JSON
            if (dataNode.isTextual()) {
                String rawData = dataNode.asText();
                dataNode = mapper.readTree(rawData);
            }

            return new JsonResponse(code, dataNode);
        }
    }

    /**
     * 重试请求工作流方法，最多尝试3次，每次等待最多3分钟
     * @param parameters 请求参数
     * @return JsonResponse 响应结果
     * @throws Exception 超过最大重试次数后抛出异常
     */
    public static JsonResponse executeWorkflowWithRetry(JsonNode parameters) throws Exception {
        final int TIMEOUT_MINUTES = 3;
        final int MAX_ATTEMPTS = 3;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Exception lastException = null;
        int attempt = 0;

        while (attempt < MAX_ATTEMPTS) {
            attempt++;
            System.err.println("开始请求工作流，第 " + attempt + " 次尝试。。。");

            Future<JsonResponse> future = executor.submit(() -> executeWorkflow(parameters));

            try {
                // 等待最多 3 分钟
                JsonResponse response = future.get(TIMEOUT_MINUTES, TimeUnit.MINUTES);
                System.err.println("工作流请求结束。。。");
                executor.shutdown();
                return response;
            } catch (TimeoutException e) {
                System.err.println("请求超时超过 " + TIMEOUT_MINUTES + " 分钟，重试中。。。");
                lastException = e;
                future.cancel(true);
            } catch (Exception e) {
                System.err.println("请求出现异常：" + e.getMessage() + "，重试中。。。");
                lastException = e;
            }
        }
        executor.shutdownNow();
        throw new Exception("工作流请求超过最大尝试次数 " + MAX_ATTEMPTS, lastException);
    }

    public static class JsonResponse {
        private final int code;
        private final JsonNode data;

        public JsonResponse(int code, JsonNode data) {
            this.code = code;
            this.data = data;
        }

        public int getCode() { return code; }
        public JsonNode getData() { return data; }
    }
}
