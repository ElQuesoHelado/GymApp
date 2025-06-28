package com.cancheros.GymApp.Dominio.Notificaciones;

import java.util.*;
import java.time.*;

public class TipoNotificacion {
    private int id;
    private String nombre;
    private Notificacion Notificacion;
    private Notificacion tiene;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Notificacion getNotificacion() {
        return Notificacion;
    }

    public Notificacion getTiene() {
        return tiene;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNotificacion(Notificacion Notificacion) {
        this.Notificacion = Notificacion;
    }

    public void setTiene(Notificacion tiene) {
        this.tiene = tiene;
    }

    public void linkNotificacion(Notificacion _Notificacion) {
        if (_Notificacion != null) {
            _Notificacion.unlinkTipoNotificacion();
            _Notificacion.setTipoNotificacion(this);
        }

        unlinkNotificacion();
        setNotificacion(_Notificacion);
    }

    public void linkTiene(Notificacion _tiene) {
//        if (_tiene != null) {
//            _tiene.unlink();
//            _tiene.set(this);
//        }
//
//        unlinkTiene();
//        setTiene(_tiene);
    }

    public void unlinkNotificacion() {
        if (getNotificacion() != null) {
            getNotificacion().setTipoNotificacion(null);
            setNotificacion(null);
        }
    }

    public void unlinkTiene() {
        if (getTiene() != null) {
            getTiene().set(null);
            setTiene(null);
        }
    }
}