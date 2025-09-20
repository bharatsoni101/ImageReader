package com.ir.imagereader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageReaderController {
    private final ChatClient chatClient;

    @Value("classpath:/images/HotAirBalloon.JPG")
    private Resource sampleImage;

    @Autowired
    public ImageReaderController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/image-to-text")
    @ResponseBody
    public String imageToText(@RequestParam("prompt") String prompt) {
        return chatClient.promptWithImage(prompt, sampleImage);
    }

    @GetMapping("/chat")
    @ResponseBody
    public String chat(@RequestParam("prompt") String prompt) {
        String jsonResponse = chatClient.promptWithText(prompt);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            // Try to extract choices[0].message.content
            JsonNode choices = root.get("choices");
            if (choices != null && choices.isArray() && choices.size() > 0) {
                JsonNode message = choices.get(0).get("message");
                if (message != null && message.has("content")) {
                    return message.get("content").asText();
                }
            }
            // Fallback to previous logic
            if (root.has("message")) {
                return root.get("message").asText();
            } else if (root.has("content")) {
                return root.get("content").asText();
            } else {
                return jsonResponse; // fallback: return raw JSON
            }
        } catch (Exception e) {
            // If parsing fails, return raw response
            return jsonResponse;
        }
    }

    @GetMapping("/")
    public String home() {
        return "home"; // Thymeleaf template name (lowercase)
    }
}
