package com.cancheros.GymApp.Dominio.Rutinas;

import java.util.*;
import java.time.*;

public class Ejercicio {
    private String nombre;
    private String descripcion;
    private int repeticiones;
    private int series;
    private Rutina Rutina;

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public int getSeries() {
        return series;
    }

    public Rutina getRutina() {
        return Rutina;
    }

    public Rutina get() {
        return Rutina;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setRutina(Rutina Rutina) {
        this.Rutina = Rutina;
    }

    public void set(Rutina Rutina) {
        this.Rutina = Rutina;
    }

    public void linkRutina(Rutina _Rutina) {
        if (_Rutina != null) {
            _Rutina.unlinkEjercicio();
            _Rutina.setEjercicio(this);
        }

        unlinkRutina();
        setRutina(_Rutina);
    }

    public void link(Rutina _Rutina) {
//        if (_Rutina != null) {
//            _Rutina.unlink();
//            _Rutina.set(this);
//        }
//
//        unlink();
//        set(_Rutina);
    }

    public void unlinkRutina() {
        if (getRutina() != null) {
            getRutina().setEjercicio(null);
            setRutina(null);
        }
    }

    public void unlink() {
        if (get() != null) {
            get().set(null);
            set(null);
        }
    }

    public float calcularCaloriasQuemadas(int duracion) {
        return 0;
    }
}