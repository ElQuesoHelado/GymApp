package com.soft.gymapp.servicios.dto;

import com.soft.gymapp.dominio.notificaciones.TipoNotificacion;

import java.util.Date;

public class NotificacionDTO {

    private int id;
    private String mensaje;
    private boolean leido;
    private Date fechaEnvio;
    private TipoNotificacion tipo;

    public NotificacionDTO(
            int id,
            String mensaje,
            boolean leido,
            Date fechaEnvio,
            TipoNotificacion tipo
    ) {
        this.id = id;
        this.mensaje = mensaje;
        this.leido = leido;
        this.fechaEnvio = fechaEnvio;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isLeido() {
        return leido;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }
}