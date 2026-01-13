package com.soft.gymapp.servicios.dto;

import java.util.List;

public record RutinaDTO(
        int id,
        String nombre,
        String objetivo,
        List<EjercicioDTO> ejercicios
) {
}
