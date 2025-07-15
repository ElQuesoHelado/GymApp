package com.soft.gymapp.dominio.sesiones;

import jakarta.persistence.Embeddable;

import java.sql.Time;
import java.util.*;

@Embeddable
public class Horario {
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;

    public boolean esDisponible() {
        Date ahora = new Date();
        Time horaActual = new Time(ahora.getTime());

        if (horaInicio == null || horaFin == null || fecha == null) {
            throw new IllegalStateException("Horario invalido");
        }
        if (horaInicio.after(horaFin)) {
            return false;
        }

        return fecha.after(ahora) || (fecha.equals(ahora) && horaFin.after(horaActual));
    }

}