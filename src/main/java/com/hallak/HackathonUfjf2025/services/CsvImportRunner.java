package com.hallak.HackathonUfjf2025.services; // Ou qualquer pacote


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class CsvImportRunner implements CommandLineRunner {

    @Autowired
    private DataImportServiceImpl importacaoService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO CARGA DE DADOS CSV ---");

        try {

            System.out.println("Carregando bovinos...");
            try (InputStream is = new ClassPathResource("csv/bovinos_limpo.csv").getInputStream()) {
                importacaoService.importarBovinos(is);
            }
            System.out.println("Bovinos carregados.");


            System.out.println("Carregando eventos...");
            try (InputStream is = new ClassPathResource("csv/ocorrenciaEvento.csv").getInputStream()) {
                importacaoService.importarEventos(is);
            }
            System.out.println("Eventos carregados.");

            System.out.println("Carregando fichas de lactação...");
            try (InputStream is = new ClassPathResource("csv/fichalactacao_1_.csv").getInputStream()) {
                importacaoService.importarFichasLactacao(is);
            }
            System.out.println("Fichas de lactação carregadas.");

            System.out.println("--- CARGA DE DADOS CSV CONCLUÍDA ---");

        } catch (Exception e) {
            System.err.println("!!! ERRO DURANTE A IMPORTAÇÃO CSV !!!");
            System.out.println(e.getMessage());
        }
    }
}