package com.soft.gymapp.dominio.planesentrenamiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaRepositorio extends JpaRepository<Rutina, Integer> {
    // JpaRepository ya nos da guardar, listar, buscar por ID, etc.
}