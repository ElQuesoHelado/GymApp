package com.soft.gymapp.dominio.sesiones;

import jakarta.persistence.Embeddable;

import java.sql.Time;
import java.util.*;

@Embeddable
public class Horario {
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;

    //?No necesario por ValueObject?
//    @OneToOne
//    private Sesion sesion;

    public boolean esDisponible() {

        return false;
    }

}