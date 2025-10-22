package com.hallak.HackathonUfjf2025.repositories;

import com.hallak.HackathonUfjf2025.entities.Bovino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BovinoRepository extends JpaRepository<Bovino, String> {
}
