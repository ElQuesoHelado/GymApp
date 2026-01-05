package com.soft.gymapp.sesiones.dto;

import com.soft.gymapp.sesiones.dominio.Horario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para crear nuevas sesiones.
 * Se utiliza en las solicitudes POST de la API REST.
 */
public class CrearSesionDTO {

    @NotNull(message = "El ID del plan de entrenamiento es obligatorio")
    private Long idPlanEntrenamiento;

    @NotNull(message = "El ID del entrenador es obligatorio")
    private Long idEntrenador;

    private Long idSala;

    @Valid
    @NotNull(message = "El horario es obligatorio")
    private Horario horario;

    private String descripcion;

    // Constructores
    public CrearSesionDTO() {}

    public CrearSesionDTO(Long idPlanEntrenamiento, Long idEntrenador, Horario horario) {
        this.idPlanEntrenamiento = idPlanEntrenamiento;
        this.idEntrenador = idEntrenador;
        this.horario = horario;
    }

    // Getters y Setters
    public Long getIdPlanEntrenamiento() { return idPlanEntrenamiento; }
    public void setIdPlanEntrenamiento(Long idPlanEntrenamiento) { this.idPlanEntrenamiento = idPlanEntrenamiento; }

    public Long getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(Long idEntrenador) { this.idEntrenador = idEntrenador; }

    public Long getIdSala() { return idSala; }
    public void setIdSala(Long idSala) { this.idSala = idSala; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
