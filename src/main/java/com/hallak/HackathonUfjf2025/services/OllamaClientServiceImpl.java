package com.hallak.HackathonUfjf2025.services;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.stereotype.Service;

@Service
public class OllamaClientServiceImpl implements OllamaClientService {

    private final ChatClient chatClient;

    @Autowired
    public OllamaClientServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }



    @Override
    public String newCall(String query) {
        Prompt prompt = new Prompt(query);
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
