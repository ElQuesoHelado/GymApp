package com.soft.gymapp.dominio.sesiones;

import com.soft.gymapp.servicios.dto.SalaDTO;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "sala")
public class Sala {
    @Id
    public int id;
    private String nombre;
    private int capacidad;

    @OneToMany(mappedBy = "sala")
    private List<Sesion> sesiones = new ArrayList<>();

    public SalaDTO toDTO() {
        return new SalaDTO(this.id, this.nombre, this.capacidad);
    }

    public boolean verDisponibilidad(Date fecha) {

        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }
}