package com.cancheros.GymApp.Dominio.Sesiones;

import java.util.*;
import java.time.*;

public class Sala {
    private int id;
    private String nombre;
    private int capacidad;
    private Sesion Sesion;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Sesion getSesion() {
        return Sesion;
    }

    public Sesion get() {
        return Sesion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setSesion(Sesion Sesion) {
        this.Sesion = Sesion;
    }

    public void set(Sesion Sesion) {
        this.Sesion = Sesion;
    }

    public void linkSesion(Sesion _Sesion) {
        if (_Sesion != null) {
            _Sesion.unlinkSala();
            _Sesion.setSala(this);
        }

        unlinkSesion();
        setSesion(_Sesion);
    }

    public void link(Sesion _Sesion) {
//        if (_Sesion != null) {
//            _Sesion.unlink();
//            _Sesion.set(this);
//        }
//
//        unlink();
//        set(_Sesion);
    }


    public void unlinkSesion() {
        if (getSesion() != null) {
            getSesion().setSala(null);
            setSesion(null);
        }
    }

    public void unlink() {
//        if (get() != null) {
//            get().set(null);
//            set(null);
//        }
    }

    public boolean verDisponibilidad(Date fecha) {

        return false;
    }
}