package com.ruoyi.system.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.system.domain.DeepSeekRequest;
import com.ruoyi.system.domain.DeepSeekResponse;
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
}
