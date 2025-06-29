package com.cancheros.GymApp.Dominio.Sesiones;

import com.cancheros.GymApp.Dominio.Rutinas.Ejercicio;
import com.cancheros.GymApp.Dominio.Rutinas.Rutina;
import jakarta.persistence.*;

import java.util.*;
import java.time.*;

@Entity
@Table(name = "sala")
public class Sala {
    @Id
    private int id;
    private String nombre;
    private int capacidad;

    @OneToMany
    private List<Sesion> sesiones = new ArrayList<>();

    public boolean verDisponibilidad(Date fecha) {

        return false;
    }
}