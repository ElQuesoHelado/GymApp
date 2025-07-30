package com.soft.gymapp.dominio.usuarios;

import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.sesiones.Sesion;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "entrenador")
public class Entrenador extends Usuario {
    private String especialidad;
    private String certificaciones;

    @OneToMany(mappedBy = "entrenador")
    private List<Sesion> sesiones = new ArrayList<>();

    @OneToMany(mappedBy = "entrenador")
    private List<PlanEntrenamiento> planes;

    public void crearRutina() {

    }

    public void verClientes() {

    }
}