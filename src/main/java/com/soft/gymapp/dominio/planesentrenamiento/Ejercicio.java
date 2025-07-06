package com.soft.gymapp.dominio.planesentrenamiento;

import jakarta.persistence.*;

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
    private Rutina rutina;

    public float calcularCaloriasQuemadas(int duracion) {
        return 0;
    }
}