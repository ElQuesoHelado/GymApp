package com.soft.gymapp.planesentrenamiento.dominio;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan_entrenamiento")
public class PlanEntrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate fechaInicio;
    private int duracionSemanas;
    @ManyToMany
    private List<Rutina> rutinas = new ArrayList<>();
    // Getters y setters
}
