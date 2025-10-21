package com.hallak.HackathonUfjf2025.entities;

import lombok.Getter;

@Getter
public enum TipoAssociacaoEspecime {
    PAI(1),
    MAE(2);

    private final int codigo;

    TipoAssociacaoEspecime(int codigo) {
        this.codigo = codigo;

    }
}
