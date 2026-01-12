package com.soft.gymapp.notificaciones.dominio;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;
    
    @Column(name = "fecha_envio")
    private Date fechaEnvio;
    
    private boolean leido;

    @Column(name = "id_usuario")
    private Long usuarioId; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;

    // Constructor por defecto
    public Notificacion() {
        this.fechaEnvio = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        this.leido = false;
    }

    public Notificacion(String mensaje, Long usuarioId, TipoNotificacion tipo) {
        this();
        this.mensaje = mensaje;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public void marcarComoLeida() {
        this.leido = true;
    }
}