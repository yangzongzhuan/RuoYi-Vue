package com.ruoyi.system.coze.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
