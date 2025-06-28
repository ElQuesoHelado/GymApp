package com.cancheros.GymApp.Dominio.Membresias;

import java.util.*;
import java.time.*;

public class TipoMembresia {

    private String nombre;

    private int duracionDias;

    private float precio;

    private Membresia membresia;

    public String getNombre() {
        return nombre;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public float getPrecio() {
        return precio;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public Membresia get() {
        return membresia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public void set(Membresia membresia) {
        this.membresia = membresia;
    }


    public void linkMembresia(Membresia _membresia) {
        if (_membresia != null) {
            _membresia.unlinkTipomenbresia();
            _membresia.setTipomenbresia(this);
        }

        unlinkMembresia();
        setMembresia(_membresia);
    }

    public void link(Membresia _membresia) {
//        if (_membresia != null) {
//            _membresia.unlink();
//            _membresia.set(this);
//        }
//
//        unlink();
//        set(_membresia);
    }

    public void unlinkMembresia() {
        if (getMembresia() != null) {
            getMembresia().setTipomenbresia(null);
            setMembresia(null);
        }
    }

    public void unlink() {
//        if (get() != null) {
//            get().set(null);
//            set(null);
//        }
    }
}