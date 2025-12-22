package com.soft.gymapp.dominio.sesiones;

import jakarta.persistence.Embeddable;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    // Y setters si no los tienes:
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}