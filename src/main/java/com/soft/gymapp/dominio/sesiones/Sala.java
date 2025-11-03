package com.soft.gymapp.dominio.sesiones;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "sala")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private int capacidad;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @OneToMany(mappedBy = "sala")
    private List<Sesion> sesiones = new ArrayList<>();

    public boolean verDisponibilidad(Date fecha) {

        return false;
    }
}