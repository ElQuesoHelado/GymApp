package com.soft.gymapp.dominio.sesiones;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SesionRepositorio extends JpaRepository<Sesion, Integer> {
    // Buscar sesiones por estado
    List<Sesion> findByEstado(EstadoSesion estado);

    // Buscar sesiones por entrenador
    List<Sesion> findByEntrenadorId(Integer entrenadorId);

    // Buscar sesiones por sala
    List<Sesion> findBySalaId(Integer salaId);
}