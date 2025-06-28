package com.cancheros.GymApp.Dominio.Notificaciones;

import com.cancheros.GymApp.Dominio.Usuarios.Usuario;

import java.util.*;
import java.time.*;

public class Notificacion {
    private int id;
    private String mensaje;
    private Date fechaEnvio;
    private boolean leido;
    private Usuario Usuario;
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
        return Usuario;
    }

    public TipoNotificacion getTipoNotificacion() {
        return TipoNotificacion;
    }

    public Usuario get() {
        return Usuario;
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

    public void setUsuario(Usuario Usuario) {
        this.Usuario = Usuario;
    }

    public void setTipoNotificacion(TipoNotificacion TipoNotificacion) {
        this.TipoNotificacion = TipoNotificacion;
    }

    public void set(Usuario Usuario) {
        this.Usuario = Usuario;
    }

    public void linkUsuario(Usuario _Usuario) {
        if (_Usuario != null) {
            _Usuario.unlinkNotificacion();
            _Usuario.setNotificacion(this);
        }

        unlinkUsuario();
        setUsuario(_Usuario);
    }

    public void linkTipoNotificacion(TipoNotificacion _TipoNotificacion) {
        if (_TipoNotificacion != null) {
            _TipoNotificacion.unlinkNotificacion();
            _TipoNotificacion.setNotificacion(this);
        }

        unlinkTipoNotificacion();
        setTipoNotificacion(_TipoNotificacion);
    }

    public void link(Usuario _Usuario) {
//        if (_Usuario != null) {
//            _Usuario.unlink();
//            _Usuario.set(this);
//        }
//
//        unlink();
//        set(_Usuario);
    }

    public void unlinkUsuario() {
        if (getUsuario() != null) {
            getUsuario().setNotificacion(null);
            setUsuario(null);
        }
    }

    public void unlinkTipoNotificacion() {
        if (getTipoNotificacion() != null) {
            getTipoNotificacion().setNotificacion(null);
            setTipoNotificacion(null);
        }
    }

    public void unlink() {
//        if (get() != null) {
//            get().set(null);
//            set(null);
//        }
    }

    public void marcarComoLeida() {

    }

    public void enviar() {

    }

}