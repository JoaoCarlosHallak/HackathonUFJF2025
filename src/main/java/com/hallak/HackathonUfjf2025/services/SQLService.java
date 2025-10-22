package com.hallak.HackathonUfjf2025.services;

import java.util.List;
import java.util.Map;

public interface SQLService {
    List<Map<String, Object>> retornoDaConsulta(String pergunta);
}
