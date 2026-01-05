package com.soft.gymapp.sesiones.dominio;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa una sesión de entrenamiento.
 */
@Entity
@Table(name = "sesiones")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_plan_entrenamiento", nullable = false)
    private Long idPlanEntrenamiento;

    @Column(name = "id_entrenador", nullable = false)
    private Long idEntrenador;

    @Column(name = "id_sala")
    private Long idSala;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoSesion estado = EstadoSesion.SIN_EMPEZAR;

    @Embedded
    private Horario horario;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "numero_participantes")
    private Integer numeroParticipantes = 0;

    // Constructores
    public Sesion() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Sesion(Long idPlanEntrenamiento, Long idEntrenador, Horario horario) {
        this();
        this.idPlanEntrenamiento = idPlanEntrenamiento;
        this.idEntrenador = idEntrenador;
        this.horario = horario;
    }

    // Métodos de lógica de negocio
    public void confirmar() {
        if (this.estado == EstadoSesion.SIN_EMPEZAR) {
            this.estado = EstadoSesion.EN_PROGRESO;
        } else {
            throw new IllegalStateException("Solo se pueden confirmar sesiones sin empezar");
        }
    }

    public void cancelar() {
        if (this.estado != EstadoSesion.TERMINADA) {
            this.estado = EstadoSesion.CANCELADA;
        } else {
            throw new IllegalStateException("No se puede cancelar una sesión terminada");
        }
    }

    public void terminar() {
        if (this.estado == EstadoSesion.EN_PROGRESO) {
            this.estado = EstadoSesion.TERMINADA;
        } else {
            throw new IllegalStateException("Solo se pueden terminar sesiones en progreso");
        }
    }

    public void reprogramar(Horario nuevoHorario) {
        if (this.estado == EstadoSesion.SIN_EMPEZAR) {
            this.horario = nuevoHorario;
        } else {
            throw new IllegalStateException("No se puede reprogramar una sesión en progreso o terminada");
        }
    }

    public void agregarParticipante() {
        this.numeroParticipantes++;
    }

    public void removerParticipante() {
        if (this.numeroParticipantes > 0) {
            this.numeroParticipantes--;
        }
    }

    public boolean estaDisponible() {
        return (this.estado == EstadoSesion.SIN_EMPEZAR) && 
               (this.horario != null && this.horario.esDisponible());
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPlanEntrenamiento() {
        return idPlanEntrenamiento;
    }

    public void setIdPlanEntrenamiento(Long idPlanEntrenamiento) {
        this.idPlanEntrenamiento = idPlanEntrenamiento;
    }

    public Long getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(Long idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public EstadoSesion getEstado() {
        return estado;
    }

    public void setEstado(EstadoSesion estado) {
        this.estado = estado;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public void setNumeroParticipantes(Integer numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }
}
