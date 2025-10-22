package com.hallak.HackathonUfjf2025.repositories;

import com.hallak.HackathonUfjf2025.entities.OcorrenciaEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface OcorrenciaEventoRepository extends JpaRepository<OcorrenciaEvento, Long> {
}