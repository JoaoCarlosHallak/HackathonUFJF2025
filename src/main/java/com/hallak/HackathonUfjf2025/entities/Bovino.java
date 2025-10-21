package com.hallak.HackathonUfjf2025.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bovino {



    @Id
    @Column(unique = true, nullable = false)
    private String codigo;

    private LocalDateTime dataNascimento;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String paisOrigem;

    @Column(nullable = false, name = "raca_id")
    private String raca;

    @Column
    private String sexo;

    @OneToMany(mappedBy = "bovino", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OcorrenciaEvento> eventos;


    @OneToMany(mappedBy = "ascendente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssociacaoBovino> descendentes;

    @OneToMany(mappedBy = "bovino", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssociacaoBovino> ascendentes;


}
