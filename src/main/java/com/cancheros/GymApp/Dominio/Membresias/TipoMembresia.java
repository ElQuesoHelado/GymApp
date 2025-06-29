package com.cancheros.GymApp.Dominio.Membresias;

import jakarta.persistence.Embeddable;

import java.util.*;
import java.time.*;

//?Es un value object?
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}