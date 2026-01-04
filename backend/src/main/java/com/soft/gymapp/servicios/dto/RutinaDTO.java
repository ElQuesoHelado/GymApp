package com.soft.gymapp.servicios.dto;

import java.util.List;

public class RutinaDTO {
    private int id;
    private String nombre;
    private String objetivo;
    private List<EjercicioDTO> ejercicios;

    public RutinaDTO(int id, String nombre, String objetivo, List<EjercicioDTO> ejercicios) {
        this.id = id;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.ejercicios = ejercicios;
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

    public List<EjercicioDTO> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<EjercicioDTO> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
