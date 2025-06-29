package com.cancheros.GymApp.Dominio.Rutinas;

import jakarta.persistence.*;

import java.util.*;
import java.time.*;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {
    @Id
    private Integer id;

    private String nombre;
    private String descripcion;
    private int repeticiones;
    private int series;

    @ManyToOne
    private Rutina Rutina;

    public float calcularCaloriasQuemadas(int duracion) {
        return 0;
    }
}