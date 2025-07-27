package com.soft.gymapp.dominio.planesentrenamiento;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "rutina")
public class Rutina {
    @Id
    private int id;
    private String nombre;
    private String objetivo;

    @ManyToMany(mappedBy = "rutinas")
    private List<PlanEntrenamiento> planEntrenamiento;

    //Composicion
    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ManyToMany
    private List<Ejercicio> ejercicios = new ArrayList<>();

    public void agregarEjercicio(Ejercicio ejercicio) {

    }

    public void quitarEjercicio(Ejercicio ejercicio) {

    }

    public void mostrarRutina() {

    }

}