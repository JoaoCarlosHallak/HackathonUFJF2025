package com.hallak.HackathonUfjf2025.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OcorrenciaEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataOcorrencia;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private DTipoEvento tipo_evento;

    @ManyToOne
    @JoinColumn(name = "bovino_id", nullable = false)
    private Bovino bovino;





}
