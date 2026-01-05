package com.soft.gymapp.sesiones.dto;

import com.soft.gymapp.sesiones.dominio.EstadoSesion;
import com.soft.gymapp.sesiones.dominio.Horario;
import java.time.LocalDateTime;

/**
 * DTO para transferencia de datos de Sesión en respuestas.
 */
public class SesionDTO {

    private Long id;
    private Long idPlanEntrenamiento;
    private Long idEntrenador;
    private Long idSala;
    private EstadoSesion estado;
    private Horario horario;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private Integer numeroParticipantes;

    // Constructores
    public SesionDTO() {}

    public SesionDTO(Long id, Long idPlanEntrenamiento, Long idEntrenador, Long idSala,
                     EstadoSesion estado, Horario horario, String descripcion,
                     LocalDateTime fechaCreacion, Integer numeroParticipantes) {
        this.id = id;
        this.idPlanEntrenamiento = idPlanEntrenamiento;
        this.idEntrenador = idEntrenador;
        this.idSala = idSala;
        this.estado = estado;
        this.horario = horario;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.numeroParticipantes = numeroParticipantes;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdPlanEntrenamiento() { return idPlanEntrenamiento; }
    public void setIdPlanEntrenamiento(Long idPlanEntrenamiento) { this.idPlanEntrenamiento = idPlanEntrenamiento; }

    public Long getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(Long idEntrenador) { this.idEntrenador = idEntrenador; }

    public Long getIdSala() { return idSala; }
    public void setIdSala(Long idSala) { this.idSala = idSala; }

    public EstadoSesion getEstado() { return estado; }
    public void setEstado(EstadoSesion estado) { this.estado = estado; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Integer getNumeroParticipantes() { return numeroParticipantes; }
    public void setNumeroParticipantes(Integer numeroParticipantes) { this.numeroParticipantes = numeroParticipantes; }
}
