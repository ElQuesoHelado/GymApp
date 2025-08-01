package com.soft.gymapp.dominio.sesiones;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "sala")
public class Sala {
    @Id
    private int id;
    private String nombre;
    private int capacidad;

    @OneToMany(mappedBy = "sala")
    private List<Sesion> sesiones = new ArrayList<>();

    public boolean verDisponibilidad(Date fecha) {

        return false;
    }
}