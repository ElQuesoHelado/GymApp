package com.soft.gymapp.dominio.sesiones;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Embeddable
public class Horario {
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public boolean esDisponible() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime horaActual = ahora.toLocalTime();
        LocalDate fechaActual = ahora.toLocalDate();

        if (horaInicio == null || horaFin == null || fecha == null) {
            throw new IllegalStateException("Horario invalido");
        }
        if (horaInicio.isAfter(horaFin)) {
            return false;
        }

        return fecha.isAfter(fechaActual) ||
                (fecha.equals(fechaActual) && horaFin.isAfter(horaActual));
    }
}