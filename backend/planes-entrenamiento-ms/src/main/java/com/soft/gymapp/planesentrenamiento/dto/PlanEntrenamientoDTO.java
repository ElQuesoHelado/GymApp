package com.soft.gymapp.planesentrenamiento.dto;

import java.time.LocalDate;
import java.util.List;

public class PlanEntrenamientoDTO {
    private int id;
    private LocalDate fechaInicio;
    private int duracionSemanas;
    private List<RutinaDTO> rutinas;
    // Getters y setters
}
