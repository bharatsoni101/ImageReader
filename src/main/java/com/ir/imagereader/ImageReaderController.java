package com.ir.imagereader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageReaderController {
    private final ChatClient chatClient;

    @Value("classpath:/images/HotAirBalloon.JPG")
    private Resource sampleImage;

    @Autowired
    public ImageReaderController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/image-to-text")
    @ResponseBody
    public String imageToText(@RequestParam("prompt") String prompt,
                              @RequestParam("image") MultipartFile image) {
        return chatClient.promptWithImage(prompt, image);
    }

    @GetMapping("/chat")
    @ResponseBody
    public String chat(@RequestParam("prompt") String prompt) {
        return chatClient.promptWithText(prompt);
    }

    @GetMapping("/")
    public String home() {
        return "home"; // Thymeleaf template name (lowercase)
    }
}
