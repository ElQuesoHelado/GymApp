package com.soft.gymapp.servicios.dto;

public record ClienteAsignadoDTO(
    Integer id,
    String nombreCompleto,
    String objetivo,
    String nivel,
    String estado,
    String ultimaAsistencia
) {}