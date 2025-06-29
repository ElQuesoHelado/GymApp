package com.cancheros.GymApp.Dominio.Usuarios;

import com.cancheros.GymApp.Dominio.Membresias.Membresia;
import com.cancheros.GymApp.Dominio.Rutinas.PlanEntrenamiento;
import com.cancheros.GymApp.Dominio.Sesiones.Sesion;
import jakarta.persistence.*;

import java.util.*;
import java.time.*;

@Entity
@Table(name = "cliente")
public class Cliente extends Usuario {
    private String objetivo;
    private String nivel;

    @OneToOne
    private Membresia Membresia;

    @OneToOne
    private PlanEntrenamiento PlanEntrenamiento;

    @ManyToMany
    private List<Cliente> sesiones = new ArrayList<>();

    public void verRutina() {

    }

    public void reservarCita() {

    }
}