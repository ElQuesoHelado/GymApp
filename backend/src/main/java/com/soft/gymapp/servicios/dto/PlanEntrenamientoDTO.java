package com.soft.gymapp.servicios.dto;

import java.time.LocalDate;
import java.util.List;

public record PlanEntrenamientoDTO(
        int id,
        LocalDate fechaInicio,
        int duracionSemanas,
        Integer clienteId,
        Integer entrenadorId,
        List<RutinaDTO> rutinas
) {
}
