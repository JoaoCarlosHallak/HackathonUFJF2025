package com.hallak.HackathonUfjf2025.entities;

import jakarta.persistence.*;



@Entity
public class AssociacaoBovino{

    @EmbeddedId
    private AssociacaoBovinoId id;

    @ManyToOne
    @MapsId("ascendenteId")
    @JoinColumn(nullable = false, name = "ascendente_id")
    private Bovino ascendente;

    @ManyToOne
    @MapsId("bovinoId")
    @JoinColumn(nullable = false, name = "bovino_id")
    private Bovino bovino;

    @Column(nullable = false)
    private int situacao;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TipoAssociacaoEspecime tipo;


}
