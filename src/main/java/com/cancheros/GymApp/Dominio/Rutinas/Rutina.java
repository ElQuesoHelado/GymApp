package com.cancheros.GymApp.Dominio.Rutinas;

import com.cancheros.GymApp.Dominio.Usuarios.Entrenador;

import java.util.*;
import java.time.*;

public class Rutina {

    private int id;
    private String nombre;
    private String objetivo;
    private Entrenador Entrenador;
    private PlanEntrenamiento PlanEntrenamiento;
    private Ejercicio Ejercicio;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public Entrenador getEntrenador() {
        return Entrenador;
    }

    public PlanEntrenamiento getPlanEntrenamiento() {
        return PlanEntrenamiento;
    }

    public Ejercicio getEjercicio() {
        return Ejercicio;
    }

    public Entrenador get() {
        return Entrenador;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void setEntrenador(Entrenador Entrenador) {
        this.Entrenador = Entrenador;
    }

    public void setPlanEntrenamiento(PlanEntrenamiento PlanEntrenamiento) {
        this.PlanEntrenamiento = PlanEntrenamiento;
    }

    public void setEjercicio(Ejercicio Ejercicio) {
        this.Ejercicio = Ejercicio;
    }

    public void set(Entrenador Entrenador) {
        this.Entrenador = Entrenador;
    }


    public void linkEntrenador(Entrenador _Entrenador) {
//        if (_Entrenador != null) {
//            _Entrenador.unlinkRutina();
//            _Entrenador.setRutina(this);
//        }
//
//        unlinkEntrenador();
//        setEntrenador(_Entrenador);
    }

    public void linkPlanEntrenamiento(PlanEntrenamiento _PlanEntrenamiento) {
        if (_PlanEntrenamiento != null) {
            _PlanEntrenamiento.unlinkRutina();
            _PlanEntrenamiento.setRutina(this);
        }

        unlinkPlanEntrenamiento();
        setPlanEntrenamiento(_PlanEntrenamiento);
    }


    public void link(Entrenador _Entrenador) {
//        if (_Entrenador != null) {
//            _Entrenador.unlink();
//            _Entrenador.set(this);
//        }
//
//        unlink();
//        set(_Entrenador);
    }

    public void unlinkEntrenador() {
//        if (getEntrenador() != null) {
//            getEntrenador().setRutina(null);
//            setEntrenador(null);
//        }
    }

    public void unlinkPlanEntrenamiento() {
        if (getPlanEntrenamiento() != null) {
            getPlanEntrenamiento().setRutina(null);
            setPlanEntrenamiento(null);
        }
    }

    public void unlinkEjercicio() {
        if (getEjercicio() != null) {
            getEjercicio().setRutina(null);
            setEjercicio(null);
        }
    }

    public void unlink() {
//        if (get() != null) {
//            get().set(null);
//            set(null);
//        }
    }

    public void agregarEjercicio(Ejercicio ejercicio) {

    }

    public void quitarEjercicio(Ejercicio ejercicio) {

    }

    public void mostrarRutina() {

    }

}