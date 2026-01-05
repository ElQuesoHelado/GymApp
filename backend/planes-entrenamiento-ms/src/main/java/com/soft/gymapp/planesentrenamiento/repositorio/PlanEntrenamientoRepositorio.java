package com.soft.gymapp.planesentrenamiento.repositorio;

import com.soft.gymapp.planesentrenamiento.dominio.PlanEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanEntrenamientoRepositorio extends JpaRepository<PlanEntrenamiento, Integer> {
}
