package com.cancheros.GymApp.Dominio.Rutinas;

import com.cancheros.GymApp.Dominio.Usuarios.Entrenador;
import jakarta.persistence.*;

import java.util.*;
import java.time.*;

@Entity
@Table(name = "rutina")
public class Rutina {
    @Id
    private int id;
    private String nombre;
    private String objetivo;

    @ManyToOne
    private PlanEntrenamiento planEntrenamiento;

    //Composicion
    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejercicio> ejercicios = new ArrayList<>();

    public void agregarEjercicio(Ejercicio ejercicio) {

    }

    public void quitarEjercicio(Ejercicio ejercicio) {

    }

    public void mostrarRutina() {

    }

}