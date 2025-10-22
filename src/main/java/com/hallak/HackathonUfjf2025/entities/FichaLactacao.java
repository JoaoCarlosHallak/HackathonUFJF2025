package com.hallak.HackathonUfjf2025.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
public class FichaLactacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataEncerramento;

    @Column
    private String formaColeta;

    @Column(nullable = false)
    private int idadeMesesParto;

    @ManyToOne
    @JoinColumn(name = "codigo_bovino", referencedColumnName = "codigo", nullable = false)
    private Bovino bovino;


    @JoinColumn(name = "idEventoParto")
    private Long idEventoParto;

    @JoinColumn(name = "idEventoSeca")
    private Long idEventoSeca;

    @Column(nullable = false)
    private Integer numeroOrdenhas;

    @Column(nullable = false)
    private Double qtDeDiasLactacao;

    @Column(nullable = false)
    private Double qtDeGordura305;

    @Column(nullable = false)
    private Double qtDeLeite305;

    @Column(nullable = false)
    private Double qtDeProteina305;


}
