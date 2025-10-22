package com.hallak.HackathonUfjf2025.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SQLServiceImpl implements SQLService{

    private final OllamaClientServiceImpl ollamaClientService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SQLServiceImpl(OllamaClientServiceImpl ollamaClientService, JdbcTemplate jdbcTemplate) {
        this.ollamaClientService = ollamaClientService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> retornoDaConsulta(String pergunta){

        String sqlQueryBruta = ollamaClientService.newCall(pergunta);


        String sqlLimpo = limparSql(sqlQueryBruta);

        System.out.println("SQL (Bruto): " + sqlQueryBruta);
        System.out.println("SQL (Limpo): " + sqlLimpo);
        return jdbcTemplate.queryForList(sqlLimpo);

    }


    private String limparSql(String sql) {

        sql = sql.replace("```sql", "");
        sql = sql.replace("```", "");
        sql = sql.trim();

        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }

        return sql;
    }
}