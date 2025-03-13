package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.system.domain.DeepSeekRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class DeepSeekService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;


    private final ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public String askDeepSeek(String question) throws JsonProcessingException {

        DeepSeekRequest request = new DeepSeekRequest();
        request.setModel("deepseek-chat");
        request.setStream(false);

        List<DeepSeekRequest.Message> messages = Arrays.asList(
                new DeepSeekRequest.Message("user", question)
        );
        request.setMessages(messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey); // 手动设置Header

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), entity, String.class);

//        if (response.getStatusCode().is2xxSuccessful()) {
//
//            DeepSeekResponse deepSeekResponse = objectMapper.readValue(response.getBody(), DeepSeekResponse.class);
//
//            if (deepSeekResponse != null && deepSeekResponse.getChoices() != null && !deepSeekResponse.getChoices().isEmpty()) {
//
//                return deepSeekResponse.getChoices().get(0).getDelta().getContent();
//            }
//        }

        if (response.getStatusCode().is2xxSuccessful()) {
            // 使用 JsonPath 精准提取
            JsonNode root = objectMapper.readTree(response.getBody());
            return root.at("/choices/0/message/content").asText();
        }

        return "No valid response from DeepSeek";
    }

    public String generateNames(JSONObject config) throws JsonProcessingException {

        DeepSeekRequest request = new DeepSeekRequest();
        request.setModel("deepseek-chat");
        request.setStream(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey); // 手动设置Header

        // 构建符合DeepSeek要求的提示词结构[1,5](@ref)
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("基于以下JSON配置生成姓名：\n");
        promptBuilder.append(config);
        promptBuilder.append("\n请严格按照以下规则生成：\n");
        promptBuilder.append("1. 对于JSON中的imageConfigs数组，每个子项都有一个generateCount字段，该字段指明了需要生成的实例数量。比如，如果第一个imageConfigs的generateCount为2，则针对该模板生成2个独立的分析结果。\n");
        promptBuilder.append("2. 每个textLayerConfigs对象内，每个图层只生成一个新的姓名，不允许直接沿用原有的sampleText。\n");
        promptBuilder.append("3. 注意：有些textLayer的sampleText可能描述了与其它图层有关联的信息，请仔细分析模板中各图层间的描述关系，确保生成的姓名风格和内容合理且互不冲突。\n");
        promptBuilder.append("4. 生成的新姓名必须满足各图层对应的prompt要求，并替换原有的sampleText值。\n");
        promptBuilder.append("5. 保留原有的imageConfigs结构不变，只对每个textLayer的sampleText进行替换更新。\n");
        promptBuilder.append("6. 请使用JSON格式返回最终结果，输出的JSON结构应与原配置一致，但其中的sampleText已被替换为新生成的姓名数据。");


        // 构建多轮对话请求体[5](@ref)
        // 强化提示词约束（网页4最佳实践）
        String systemPrompt = " 你是有20年经验的起名专家，请严格遵循以下规则： 1. 仅返回合法JSON格式，首字符必须是{ 2. 禁用Markdown格式 3. 数值类型必须用双引号包裹 4. 确保所有引号闭合";
        List<DeepSeekRequest.Message> messages = Arrays.asList(
                new DeepSeekRequest.Message("system", systemPrompt),
                new DeepSeekRequest.Message("user", promptBuilder.toString())
        );

        request.setMessages(messages);

        // 发送请求（保持原有请求结构）
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);
        System.out.println("deepseek请求中。。。。。。。");
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
        System.out.println("deepseek成功！");

        // 增强响应处理
        if (response.getStatusCode().is2xxSuccessful()) {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode contentNode = root.at("/choices/0/message/content");
            System.out.println(contentNode);
            // 验证JSON格式合法性
            if (contentNode.isTextual()) {
                try {
                    return objectMapper.readTree(contentNode.asText()).toString();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("DeepSeek返回非标准JSON格式");
                }
            }
        }
        throw new RuntimeException("API请求失败：" + response.getStatusCode());
    }
}
