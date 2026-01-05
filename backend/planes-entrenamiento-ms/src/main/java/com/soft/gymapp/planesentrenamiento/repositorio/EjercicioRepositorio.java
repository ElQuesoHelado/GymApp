package com.soft.gymapp.planesentrenamiento.repositorio;

import com.soft.gymapp.planesentrenamiento.dominio.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjercicioRepositorio extends JpaRepository<Ejercicio, Integer> {
}
