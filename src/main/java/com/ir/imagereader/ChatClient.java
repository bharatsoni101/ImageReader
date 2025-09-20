package com.ir.imagereader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.*;

@Component
public class ChatClient {

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.api.model}")
    private String model;

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String promptWithImage(String prompt, Resource imageResource) {
        try {
            boolean isTextOnly = model.contains("gpt-oss") || model.toLowerCase().contains("gpt-3") || model.toLowerCase().contains("chatgpt");
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("model", model);
            List<Map<String, Object>> messages = new ArrayList<>();
            Map<String, Object> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            if (isTextOnly) {
                userMessage.put("content", prompt);
            } else {
                byte[] imageBytes = StreamUtils.copyToByteArray(imageResource.getInputStream());
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                List<Object> contentList = new ArrayList<>();
                Map<String, Object> textPart = new HashMap<>();
                textPart.put("type", "text");
                textPart.put("text", prompt);
                Map<String, Object> imagePart = new HashMap<>();
                imagePart.put("type", "image_url");
                Map<String, Object> imageUrl = new HashMap<>();
                imageUrl.put("url", "data:image/jpeg;base64," + base64Image);
                imagePart.put("image_url", imageUrl);
                contentList.add(textPart);
                contentList.add(imagePart);
                userMessage.put("content", contentList);
            }
            messages.add(userMessage);
            requestMap.put("messages", messages);
            String requestBody = objectMapper.writeValueAsString(requestMap);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            ChatCompletionResponse result = JsonConverter.fromJson(response.getBody());
            return result.getChoices().getFirst().getMessage().getContent();
        } catch (IOException e) {
            return null;
        }
    }

    public String promptWithText(String prompt) {
        try {
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("model", model);
            List<Map<String, Object>> messages = new ArrayList<>();
            Map<String, Object> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.add(userMessage);
            requestMap.put("messages", messages);
            String requestBody = objectMapper.writeValueAsString(requestMap);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            ChatCompletionResponse result = JsonConverter.fromJson(response.getBody());
            return result.getChoices().getFirst().getMessage().getContent();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
