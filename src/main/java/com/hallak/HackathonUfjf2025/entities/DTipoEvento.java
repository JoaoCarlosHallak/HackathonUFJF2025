package com.hallak.HackathonUfjf2025.entities;

import lombok.Getter;

@Getter
public enum DTipoEvento {
    PARTO(1),
    OBITO(2),
    COBERTURA(3),
    ORDENHA(4),
    PESAGEM(5),
    SECA(6),
    ABERTURA_LACTACAO(7);

    private final int codigo;

    DTipoEvento(int codigo){
        this.codigo = codigo;

    }

}
