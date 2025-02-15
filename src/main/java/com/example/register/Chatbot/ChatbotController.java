package com.example.register.Chatbot;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    private final ChatbotService openAIService;

    // Injection via constructeur (Recommandé)
    public ChatbotController(ChatbotService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/ask")
    public String askChatbot(@RequestParam String question) {
        return openAIService.genererReponse(question);
    }
}
