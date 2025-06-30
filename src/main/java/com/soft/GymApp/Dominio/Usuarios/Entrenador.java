package com.soft.GymApp.Dominio.Usuarios;

import com.soft.GymApp.Dominio.PlanesEntrenamiento.PlanEntrenamiento;
import com.soft.GymApp.Dominio.Sesiones.Sesion;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "entrenador")
public class Entrenador extends Usuario {
    private String especialidad;
    private String certificaciones;

    @OneToMany
    private List<Sesion> sesiones = new ArrayList<>();

    @OneToMany(mappedBy = "entrenador")
    private List<PlanEntrenamiento> planes;

    public void crearRutina() {

    }

    public void verClientes() {

    }
}