package com.hallak.HackathonUfjf2025.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatWebController {

    @GetMapping(value = "/chat")
    public String getIndex(){
        return "index";
    }
}
