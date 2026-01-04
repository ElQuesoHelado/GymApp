package com.soft.gymapp.dominio.membresias;

import jakarta.persistence.Embeddable;

@Embeddable
public class TipoMembresia {
    private String nombre;
    private int duracionDias;
    private float precio;

    public String getNombre() { 
        return nombre; 
    }

    public int getDuracionDias() { 
        return duracionDias; 
    }

    public float getPrecio() { 
        return precio; 
    }

    // SOLO UNA VEZ CADA SETTER - con validaciones
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    public void setDuracionDias(int duracionDias) {
        if (duracionDias <= 0) {
            throw new IllegalArgumentException("La duración debe ser positiva");
        }
        this.duracionDias = duracionDias;
    }

    public void setPrecio(float precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }

    public float calcularPrecioPorDia() {
        if (this.duracionDias <= 0) return 0;
        return this.precio / this.duracionDias;
    }
}