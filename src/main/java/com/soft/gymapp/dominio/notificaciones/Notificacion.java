package com.soft.gymapp.dominio.notificaciones;

import com.soft.gymapp.dominio.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Notificacion {
    @Id
    private int id;
    private String mensaje;
    private Date fechaEnvio;
    private boolean leido;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;

    // Constructor por defecto (Cookbook)
    public Notificacion() {
        this.fechaEnvio = Date.from(Instant.from(LocalDateTime.now()));
    }

    // Constructor con parámetros esenciales (Cookbook)
    public Notificacion(String mensaje, Usuario usuario, TipoNotificacion tipo) {
        this();
        this.mensaje = mensaje;
        this.usuario = usuario;
        this.tipo = tipo;
    }

    // Getters y Setters separados
    public int getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public boolean isLeido() {
        return leido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTipo(TipoNotificacion tipoNotificacion) {
        this.tipo = tipoNotificacion;
    }

    public void marcarComoLeida() {
        leido = true;
    }

    public void enviar() {
        if (this.usuario == null) {
            throw new IllegalStateException("No se puede enviar: usuario no asignado");
        }
        usuario.pushNotificacion(this);
    }

}