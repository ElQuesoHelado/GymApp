package com.cancheros.GymApp.Dominio.Sesiones;

import com.cancheros.GymApp.Dominio.Usuarios.Cliente;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.sql.Time;
import java.util.*;
import java.time.*;

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