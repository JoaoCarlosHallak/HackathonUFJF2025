package com.hallak.HackathonUfjf2025.services;

import com.hallak.HackathonUfjf2025.dto.RequestObject;
import com.hallak.HackathonUfjf2025.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MainServiceImpl implements MainService{

    private final OllamaClientService geminiService;

    @Autowired
    public MainServiceImpl(OllamaClientService geminiService) {
        this.geminiService = geminiService;
    }


    @Override
    public ResponseObject newQuery(RequestObject requestObject) {
        return new ResponseObject(LocalDateTime.now(), geminiService.newCall(requestObject.payload()));
    }
}
