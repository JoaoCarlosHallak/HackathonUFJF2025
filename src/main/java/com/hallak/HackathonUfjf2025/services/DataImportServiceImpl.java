package com.hallak.HackathonUfjf2025.services;

import com.hallak.HackathonUfjf2025.entities.Bovino;
import com.hallak.HackathonUfjf2025.entities.FichaLactacao;
import com.hallak.HackathonUfjf2025.entities.OcorrenciaEvento;
import com.hallak.HackathonUfjf2025.repositories.BovinoRepository;
import com.hallak.HackathonUfjf2025.repositories.FichaLactacaoRepository;
import com.hallak.HackathonUfjf2025.repositories.OcorrenciaEventoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





@Service
public class DataImportServiceImpl {

    private final BovinoRepository bovinoRepository;
    private final OcorrenciaEventoRepository ocorrenciaEventoRepository;
    private final FichaLactacaoRepository fichaLactacaoRepository;

    private static final DateTimeFormatter LACTACAO_DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DataImportServiceImpl(FichaLactacaoRepository fichaLactacaoRepository, BovinoRepository bovinoRepository, OcorrenciaEventoRepository ocorrenciaEventoRepository) {
        this.fichaLactacaoRepository = fichaLactacaoRepository;
        this.bovinoRepository = bovinoRepository;
        this.ocorrenciaEventoRepository = ocorrenciaEventoRepository;
    }

    @Transactional
    public void importarBovinos(InputStream inputStream) throws Exception {

        List<CSVRecord> records;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser parser = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                     .setHeader().setSkipHeaderRecord(true).setIgnoreEmptyLines(true).setTrim(true).build()
                     .parse(reader)) {
            records = parser.getRecords();
        }

        List<Bovino> novosBovinos = new ArrayList<>();
        for (CSVRecord record : records) {
            Bovino b = new Bovino();
            b.setCodigo(record.get("codigo"));
            b.setNome(record.get("nome"));
            b.setPaisOrigem(record.get("pais_origem"));
            b.setSexo(record.get("sexo"));
            b.setRaca(record.get("raca_id"));

            String dataNascStr = record.get("data_nascimento");
            if (dataNascStr != null && !dataNascStr.isEmpty()) {
                b.setDataNascimento(LocalDate.parse(dataNascStr).atStartOfDay());
            }
            b.setNumeroRgPai(null);
            b.setNumeroRgMae(null);
            novosBovinos.add(b);
        }
        bovinoRepository.saveAll(novosBovinos);

        Map<String, Bovino> cacheBovinos = new HashMap<>();
        bovinoRepository.findAll().forEach(b -> cacheBovinos.put(b.getCodigo(), b));
        List<Bovino> bovinosParaAtualizar = new ArrayList<>();

        for (CSVRecord record : records) {
            Bovino filho = cacheBovinos.get(record.get("codigo"));
            if (filho == null) continue;

            String codigoPai = record.get("numerorgpai");
            String codigoMae = record.get("numerorgmae");

            if (codigoPai != null && !codigoPai.isEmpty()) {
                Bovino pai = cacheBovinos.get(codigoPai);
                if (pai == null) {
                    System.err.println("AVISO: Pai com código " + codigoPai + " não encontrado para o filho " + filho.getCodigo());
                }
                filho.setNumeroRgPai(pai);
            }
            if (codigoMae != null && !codigoMae.isEmpty()) {
                Bovino mae = cacheBovinos.get(codigoMae);
                if (mae == null) {
                    System.err.println("AVISO: Mãe com código " + codigoMae + " não encontrada para o filho " + filho.getCodigo());
                }
                filho.setNumeroRgMae(mae);
            }
            bovinosParaAtualizar.add(filho);
        }
        bovinoRepository.saveAll(bovinosParaAtualizar);
    }


    @Transactional
    public void importarEventos(InputStream inputStream) throws Exception {

        Map<String, Bovino> cacheBovinos = new HashMap<>();
        bovinoRepository.findAll().forEach(b -> cacheBovinos.put(b.getCodigo(), b));
        List<OcorrenciaEvento> novosEventos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser parser = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                     .setHeader().setSkipHeaderRecord(true).setTrim(true).setIgnoreEmptyLines(true).build()
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                OcorrenciaEvento evento = new OcorrenciaEvento();


                String dataStr = record.get("dataocorrencia");
                evento.setDataOcorrencia(LocalDate.parse(dataStr).atStartOfDay());

                evento.setTipo_evento(Integer.parseInt(record.get("tipo_evento")));
                evento.setFacilidadeParto(record.get("facilidade_parto"));
                evento.setSexoCrias(record.get("sexo_crias"));

                String nroCriasStr = record.get("nro_crias");
                if (nroCriasStr != null && !nroCriasStr.isEmpty()) {
                    evento.setNroCrias(Integer.parseInt(nroCriasStr));
                }

                String qtdeLitrosStr = record.get("qtde_litros");
                if (qtdeLitrosStr != null && !qtdeLitrosStr.isEmpty()) {
                    evento.setQtdeLitros(Double.parseDouble(qtdeLitrosStr));
                }


                String codigoBovino = record.get("codigo_bovino");
                Bovino bovino = cacheBovinos.get(codigoBovino);
                if (bovino != null) {
                    evento.setBovino(bovino);
                } else {
                    System.err.println("Aviso: Bovino " + codigoBovino + " não encontrado para o evento.");
                }
                novosEventos.add(evento);
            }
        }
        ocorrenciaEventoRepository.saveAll(novosEventos);
    }


    @Transactional
    public void importarFichasLactacao(InputStream inputStream) throws Exception { 

        Map<String, Bovino> cacheBovinos = new HashMap<>();
        bovinoRepository.findAll().forEach(b -> cacheBovinos.put(b.getCodigo(), b));


        List<FichaLactacao> novasFichas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser parser = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                     .setHeader().setSkipHeaderRecord(true).setTrim(true).setIgnoreEmptyLines(true).build()
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                FichaLactacao ficha = new FichaLactacao();

                Bovino bovino = cacheBovinos.get(record.get("codigo_bovino"));
                if (bovino == null) {
                    System.err.println("Erro: Bovino " + record.get("codigo_bovino") + " não encontrado para a ficha. Pulando.");
                    continue;
                }
                ficha.setBovino(bovino);

                String idPartoStr = record.get("ideventoparto");
                if (idPartoStr != null && !idPartoStr.isEmpty()) {
                    ficha.setIdEventoParto(Long.parseLong(idPartoStr));
                }

                String idSecaStr = record.get("ideventoseca");
                if (idSecaStr != null && !idSecaStr.isEmpty()) {
                    ficha.setIdEventoSeca(Long.parseLong(idSecaStr));
                }


                ficha.setDataEncerramento(LocalDate.parse(record.get("dataencerramento"), LACTACAO_DT_FORMATTER).atStartOfDay());
                ficha.setFormaColeta(record.get("formacoleta"));
                ficha.setIdadeMesesParto(Integer.parseInt(record.get("idademesesparto")));
                ficha.setNumeroOrdenhas(Integer.parseInt(record.get("numeroordenhas")));
                ficha.setQtDeDiasLactacao(Double.parseDouble(record.get("qtdediaslactacao")));
                ficha.setQtDeLeite305(Double.parseDouble(record.get("qtdeleite305")));


                ficha.setQtDeGordura305(Double.parseDouble(record.get("qtdegordura305")));

                ficha.setQtDeProteina305(Double.parseDouble(record.get("qtdeproteina305")));

                novasFichas.add(ficha);
            }
        }
        fichaLactacaoRepository.saveAll(novasFichas);
    }
}