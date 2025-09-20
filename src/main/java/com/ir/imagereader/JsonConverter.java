package com.ir.imagereader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ChatCompletionResponse fromJson(String json) {
        try {
            return objectMapper.readValue(json, ChatCompletionResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
