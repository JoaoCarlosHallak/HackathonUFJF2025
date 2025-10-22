package com.hallak.HackathonUfjf2025.services;

import com.hallak.HackathonUfjf2025.dto.RequestObject;
import com.hallak.HackathonUfjf2025.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MainServiceImpl implements MainService{

    private final SQLService sqlService;
    private final OllamaClientService ollamaClientService;

    @Autowired
    public MainServiceImpl(SQLService sqlService, OllamaClientService ollamaClientService) {
        this.sqlService = sqlService;
        this.ollamaClientService = ollamaClientService;
    }


    @Override
    public ResponseObject newQuery(RequestObject requestObject) {
        List<Map<String, Object>> retorno = sqlService.retornoDaConsulta(requestObject.payload());
        System.out.println("RETORNO FOI: " + retorno);
        return new ResponseObject(LocalDateTime.now(), ollamaClientService.arrumarRetorno(requestObject.payload(), retorno));



    }
}
