package com.soft.GymApp.Dominio.Notificaciones;

import com.soft.GymApp.Dominio.Usuarios.Usuario;
import jakarta.persistence.*;

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
    private TipoNotificacion TipoNotificacion;

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

    public TipoNotificacion getTipoNotificacion() {
        return TipoNotificacion;
    }

    public Usuario get() {
        return usuario;
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

    public void setTipoNotificacion(TipoNotificacion TipoNotificacion) {
        this.TipoNotificacion = TipoNotificacion;
    }

    public void set(Usuario usuario) {
        this.usuario = usuario;
    }

    public void marcarComoLeida() {

    }

    public void enviar() {

    }

}