package com.soft.gymapp.servicios.dto;

import java.time.LocalDate;

public record ClienteDTO(
        int id,
        String nombre,
        String dni,
        String email,
        String telefono,
        LocalDate fechaNacimiento,
        String objetivo,
        String nivel
) {
}
