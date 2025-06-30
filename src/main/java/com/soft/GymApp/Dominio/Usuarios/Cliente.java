package com.soft.GymApp.Dominio.Usuarios;

import com.soft.GymApp.Dominio.Membresias.Membresia;
import com.soft.GymApp.Dominio.PlanesEntrenamiento.PlanEntrenamiento;
import jakarta.persistence.*;

import java.util.*;

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