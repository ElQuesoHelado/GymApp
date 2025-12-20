package com.soft.gymapp.servicios.dto;

import java.time.LocalDate;
import java.util.List;

public class PlanEntrenamientoDTO {
    private int id;
    private LocalDate fechaInicio;
    private int duracionSemanas;

    private List<RutinaDTO> rutinas;

    public PlanEntrenamientoDTO(int id, LocalDate fechaInicio, int duracionSemanas, List<RutinaDTO> rutinas) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.duracionSemanas = duracionSemanas;
        this.rutinas = rutinas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDuracionSemanas() {
        return duracionSemanas;
    }

    public void setDuracionSemanas(int duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }

    public List<RutinaDTO> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<RutinaDTO> rutinas) {
        this.rutinas = rutinas;
    }
}
