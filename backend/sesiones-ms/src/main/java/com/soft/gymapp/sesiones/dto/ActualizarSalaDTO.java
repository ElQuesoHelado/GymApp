package com.soft.gymapp.sesiones.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para actualizar salas.
 */
public class ActualizarSalaDTO {

    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @Positive(message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    private String descripcion;

    // Constructores
    public ActualizarSalaDTO() {}

    public ActualizarSalaDTO(String nombre, Integer capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
