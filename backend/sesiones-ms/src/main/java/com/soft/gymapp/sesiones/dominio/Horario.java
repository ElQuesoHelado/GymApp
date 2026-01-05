package com.soft.gymapp.sesiones.dominio;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Clase embebida que representa el horario de una sesión.
 * Contiene la fecha y hora de inicio y fin.
 */
@Embeddable
public class Horario {
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Constructores
    public Horario() {}

    public Horario(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        validar();
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Métodos de lógica de negocio
    public boolean esDisponible() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime horaActual = ahora.toLocalTime();
        LocalDate fechaActual = ahora.toLocalDate();

        if (horaInicio == null || horaFin == null || fecha == null) {
            throw new IllegalStateException("Horario inválido");
        }
        if (horaInicio.isAfter(horaFin)) {
            return false;
        }

        return fecha.isAfter(fechaActual) ||
                (fecha.equals(fechaActual) && horaFin.isAfter(horaActual));
    }

    public long getDuracionMinutos() {
        if (horaInicio == null || horaFin == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.MINUTES.between(horaInicio, horaFin);
    }

    public boolean solapaCon(Horario otro) {
        if (otro == null || !this.fecha.equals(otro.fecha)) {
            return false;
        }
        return !(this.horaFin.isBefore(otro.horaInicio) || 
                 this.horaInicio.isAfter(otro.horaFin));
    }

    private void validar() {
        if (fecha == null || horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException("Fecha y horas no pueden ser nulas");
        }
        if (horaInicio.isAfter(horaFin) || horaInicio.equals(horaFin)) {
            throw new IllegalArgumentException("Hora inicio debe ser anterior a hora fin");
        }
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}
