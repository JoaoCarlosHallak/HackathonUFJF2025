package com.hallak.HackathonUfjf2025.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OcorrenciaEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "dataocorrencia")
    private LocalDateTime dataOcorrencia;

    @Column
    private String descricao;

    @Column(nullable = false)
    private DTipoEvento tipo_evento;

    @ManyToOne
    @JoinColumn(name = "codigo_bovino", referencedColumnName = "codigo", nullable = false)
    private Bovino bovino;





}
