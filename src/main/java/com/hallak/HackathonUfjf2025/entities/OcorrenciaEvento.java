package com.hallak.HackathonUfjf2025.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OcorrenciaEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "dataocorrencia")
    private LocalDateTime dataOcorrencia;

    @Column
    private String descricao;

    @Column(nullable = false)
    private Integer tipo_evento;

    @ManyToOne
    @JoinColumn(name = "codigo_bovino", referencedColumnName = "codigo")
    private Bovino bovino;

    @Column(name = "facilidade_parto")
    private String facilidadeParto;

    @Column(name = "nro_crias")
    private Integer nroCrias;

    @Column(name = "qtde_litros")
    private Double qtdeLitros;

    @Column(name = "sexo_crias")
    private String sexoCrias;





}
