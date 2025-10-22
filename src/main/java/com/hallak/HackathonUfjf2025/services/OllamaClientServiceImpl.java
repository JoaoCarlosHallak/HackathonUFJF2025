package com.hallak.HackathonUfjf2025.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hallak.HackathonUfjf2025.utils.SystemPrompt;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OllamaClientServiceImpl implements OllamaClientService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public OllamaClientServiceImpl(ChatClient chatClient, ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
    }



    @Override
    public String newCall(String query) {
        Prompt prompt = new Prompt(SystemPrompt.getPrompt() + "\n\n--- PERGUNTA DO USUÁRIO ---\n" + query);
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

    @Override
    public String arrumarRetorno(String originalQuery, List<Map<String, Object>> retorno) {


        if (retorno == null || retorno.isEmpty()) {
            return "Não encontrei nenhum resultado para sua pergunta.";
        }


        String jsonData;
        try {

            if (retorno.size() > 20) {
                List<Map<String, Object>> sublist = retorno.subList(0, 20);
                jsonData = objectMapper.writeValueAsString(sublist) +
                           "\n... (e mais " + (retorno.size() - 20) + " resultados)";
            } else {
                jsonData = objectMapper.writeValueAsString(retorno);
            }
        } catch (JsonProcessingException e) {
            return "Erro ao formatar a resposta: " + retorno.toString();
        }


        String systemPrompt = """
        Você é uma ferramente formatação de dados. Sua única tarefa é reescrever os dados JSON a seguir em um formato de lista legível em português.

        REGRAS CRÍTICAS E OBRIGATÓRIAS:
        1.  ****NÃO PENSE, NÃO ANALISE, NÃO CONCLUA.** 
        2. APENAS LISTE OS FATOS.** Reporte os dados do JSON exatamente como eles são.
        3.  **TRADUZA AS CHAVES:** Converta as chaves do JSON para um português claro (ex: "nome" vira "Nome", "numerorgmae" vira "Mãe").
        4.  **VALORES NULOS:** Se um valor for `null` ou uma string vazia, escreva "Não informado".
        5.  **NÃO FAÇA MAIS NADA.**
        6.  **NÃO ANALISE OS DADOS.** (Ex: NÃO diga "este animal é macho pois...").
        7.  **NÃO DÊ OPINIÕES OU CONCLUSÕES.** (Ex: NÃO diga "precisamos de mais dados...").
        8.  **NÃO INVENTE CATEGORIAS.** (Ex: NÃO adicione "Filho/Filha" se não foi pedido).
        9.  Seja direto. Responda apenas com os fatos formatados.
        """;




        String userPrompt = String.format(
            "Com base na pergunta original do usuário e nos dados do banco de dados, formate os dados em uma resposta amigável, seguindo TODAS as regras.\n\n" +
            "PERGUNTA ORIGINAL: \"%s\"\n\n" +
            "DADOS DO BANCO (JSON): \n%s",
            originalQuery, jsonData
        );


        Prompt prompt = new Prompt(systemPrompt + "\n\n" + userPrompt);
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}


