package com.soft.gymapp.dominio.sesiones;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepositorio extends JpaRepository<Sala, Integer> {
    
}