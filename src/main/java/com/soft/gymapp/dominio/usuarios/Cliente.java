package com.soft.gymapp.dominio.usuarios;

import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.sesiones.Sesion;
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

    @ManyToMany(mappedBy = "clientes")
    private List<Sesion> sesiones = new ArrayList<>();

    public void verRutina() {

    }

    public void reservarCita() {

    }
}