package com.cancheros.GymApp.Dominio.Usuarios;

import com.cancheros.GymApp.Dominio.Rutinas.PlanEntrenamiento;
import com.cancheros.GymApp.Dominio.Rutinas.Rutina;
import com.cancheros.GymApp.Dominio.Sesiones.Sesion;
import jakarta.persistence.*;

import java.util.*;
import java.time.*;

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