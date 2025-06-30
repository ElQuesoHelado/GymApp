package com.soft.GymApp.Dominio.Sesiones;

import jakarta.persistence.*;

import java.util.*;

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