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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<PlanEntrenamiento> getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(List<PlanEntrenamiento> planEntrenamiento) {
        this.planEntrenamiento = planEntrenamiento;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
}