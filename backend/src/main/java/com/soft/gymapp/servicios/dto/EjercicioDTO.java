package com.soft.gymapp.servicios.dto;

public record EjercicioDTO(
        int id,
        String nombre,
        String descripcion,
        int series,
        int repeticiones
) {
}
