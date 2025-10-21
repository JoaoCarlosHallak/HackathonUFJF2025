package com.hallak.HackathonUfjf2025.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class FichaLactacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private String formaColeta;

    @Column(nullable = false)
    private int idadeMesesParto;

    @Column(nullable = false)
    private int idAnimal;

    @Column(nullable = false)
    private int idEventoParto;

    @Column(nullable = false)
    private int idEventoSeca;

    @Column(nullable = false)
    private int numeroOrdenhas;

    @Column(nullable = false)
    private Double qtDeDiasLactacao;

    @Column(nullable = false)
    private Double qtDeDeGordura305;

    @Column(nullable = false)
    private Double qtDeLeite305;

    @Column(nullable = false)
    private Double qtDeProteina305;
}
