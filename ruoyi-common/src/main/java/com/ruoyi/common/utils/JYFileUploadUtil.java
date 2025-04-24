package com.ruoyi.common.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public class JYFileUploadUtil {

    // 设置带超时配置的 RestTemplate
    private static RestTemplate getRestTemplateWithTimeout() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 连接超时时间，单位为毫秒
        factory.setReadTimeout(10000);    // 读取超时时间，单位为毫秒
        return new RestTemplate(factory);
    }

    public static String uploadFile(MultipartFile file, String fileName) {
        try {
            RestTemplate restTemplate = getRestTemplateWithTimeout();  // 使用带超时配置的RestTemplate

            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 构建请求体
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("key", API_KEY);  // API Key
            body.add("source", file.getResource());  // 上传文件
            body.add("album_id", "Su2Gf");  // 相册ID，该参数非必需，没有创建相册可自己修改代码删除
            body.add("title", fileName);  // 文件名
            body.add("format", "json");  // 响应格式

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // 发送请求
            ResponseEntity<Map> responseEntity = restTemplate.exchange(UPLOAD_URL, HttpMethod.POST, requestEntity, Map.class);

            // 解析返回结果
            Map<String, Object> responseBody = responseEntity.getBody();
            if (responseBody != null && responseBody.containsKey("image")) {
                Map<String, Object> imageInfo = (Map<String, Object>) responseBody.get("image");
                return (String) imageInfo.get("url"); // 返回图片的URL
            } else if (responseBody != null && responseBody.containsKey("error")) {
                Map<String, Object> errorInfo = (Map<String, Object>) responseBody.get("error");
                return "上传失败: " + errorInfo.get("message");
            }

            return "上传失败";
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败: " + e.getMessage();
        }
    }

    // API Key 和上传地址常量
    private static final String UPLOAD_URL = "https://www.picgo.net/api/1/upload";
    private static final String API_KEY = "chv_xUhO_4daf38920b1285c55c74cb23e709bdaec8b53ea7645db44f26b70993af495442403582f03f4790e1da2931bfb5a4419ad368a61481fba9d64aada7f1a99a92d8";
}
