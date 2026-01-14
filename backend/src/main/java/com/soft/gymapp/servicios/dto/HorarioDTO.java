package com.soft.gymapp.servicios.dto;

import java.time.LocalDate;

public record HorarioDTO(
        LocalDate fecha,
        String horaInicio,
        String horaFin
) {}