package com.hallak.HackathonUfjf2025.controllers;

import com.hallak.HackathonUfjf2025.dto.RequestObject;
import com.hallak.HackathonUfjf2025.dto.ResponseObject;
import com.hallak.HackathonUfjf2025.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class MainController {


    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> newQuery(@RequestBody RequestObject requestObject){
        return new ResponseEntity<>(mainService.newQuery(requestObject), HttpStatus.OK);
    }




}
