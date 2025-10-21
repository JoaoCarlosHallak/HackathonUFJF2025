package com.hallak.HackathonUfjf2025.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociacaoBovinoId implements Serializable {
    private int ascendenteId;
    private int bovinoId;
}
