package com.soft.gymapp.planesentrenamiento.dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String descripcion;
    private int repeticiones;
    private int series;
    @ManyToOne
    private Rutina rutina;
    // Getters y setters
}
