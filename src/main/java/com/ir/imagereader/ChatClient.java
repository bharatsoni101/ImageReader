package com.ir.imagereader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

    public String promptWithImage(String prompt, MultipartFile image) {
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
                // Prepare text content
                Map<String, Object> textContent = new HashMap<>();
                textContent.put("type", "text");
                textContent.put("text", prompt);
                // Prepare image content
                byte[] imageBytes = image.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                Map<String, Object> imageUrl = new HashMap<>();
                imageUrl.put("url", "data:image/jpeg;base64," + base64Image);
                Map<String, Object> imageContent = new HashMap<>();
                imageContent.put("type", "image_url");
                imageContent.put("image_url", imageUrl);
                // Set content as array of text and image
                userMessage.put("content", Arrays.asList(textContent, imageContent));
            }
            messages.add(userMessage);
            requestMap.put("messages", messages);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestMap), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            ChatCompletionResponse result = JsonConverter.fromJson(response.getBody());
            return result.getChoices().getFirst().getMessage().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
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
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
