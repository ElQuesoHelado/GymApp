package com.soft.gymapp.dominio.sesiones;

import com.soft.gymapp.servicios.dto.HorarioDTO;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Embeddable
public class Horario {
    private LocalDate fecha;
    private String horaInicio;
    private String horaFin;

    public HorarioDTO toDTO() {
        return new HorarioDTO(this.fecha, this.horaInicio, this.horaFin);
    }

    public boolean esDisponible() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime horaActual = ahora.toLocalTime();
        LocalDate fechaActual = ahora.toLocalDate();

        if (horaInicio == null || horaFin == null || fecha == null) {
            throw new IllegalStateException("Horario invalido");
        }
        if (LocalTime.parse(horaInicio).isAfter(LocalTime.parse(horaFin))) {
            return false;
        }

        return fecha.isAfter(fechaActual) ||
                (fecha.equals(fechaActual) && LocalTime.parse(horaFin).isAfter(horaActual));
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return LocalTime.parse(horaInicio);
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio.toString();
    }

    public LocalTime getHoraFin() {
        return LocalTime.parse(horaFin);
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin.toString();
    }
}