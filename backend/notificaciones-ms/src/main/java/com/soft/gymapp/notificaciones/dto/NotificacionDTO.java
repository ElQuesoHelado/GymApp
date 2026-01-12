package com.soft.gymapp.notificaciones.dto;

import com.soft.gymapp.notificaciones.dominio.TipoNotificacion;
import java.util.Date;

public class NotificacionDTO {
    
    private Long id;
    private String mensaje;
    private Long usuarioId;
    private TipoNotificacion tipo;
    private boolean leido;
    private Date fechaEnvio;

    // Constructor vacío
    public NotificacionDTO() {
    }

    // Constructor completo
    public NotificacionDTO(Long id, String mensaje, Long usuarioId, TipoNotificacion tipo, boolean leido, Date fechaEnvio) {
        this.id = id;
        this.mensaje = mensaje;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.leido = leido;
        this.fechaEnvio = fechaEnvio;
    }

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public TipoNotificacion getTipo() { return tipo; }
    public void setTipo(TipoNotificacion tipo) { this.tipo = tipo; }

    public boolean isLeido() { return leido; }
    public void setLeido(boolean leido) { this.leido = leido; }

    public Date getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(Date fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}