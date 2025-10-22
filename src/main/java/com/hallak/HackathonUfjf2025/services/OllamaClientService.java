package com.hallak.HackathonUfjf2025.services;

import java.util.List;
import java.util.Map;

public interface OllamaClientService {
    String newCall(String query);
    String arrumarRetorno(String originalQuery, List<Map<String, Object>> retorno);
}
