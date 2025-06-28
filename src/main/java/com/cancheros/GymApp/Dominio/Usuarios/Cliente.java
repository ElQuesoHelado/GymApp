package com.cancheros.GymApp.Dominio.Usuarios;

import com.cancheros.GymApp.Dominio.Membresias.Membresia;
import com.cancheros.GymApp.Dominio.Rutinas.PlanEntrenamiento;
import com.cancheros.GymApp.Dominio.Sesiones.Sesion;

import java.util.*;
import java.time.*;

public class Cliente extends Usuario {
    private String objetivo;
    private String nivel;
    private Membresia Membresia;
    private PlanEntrenamiento PlanEntrenamiento;
    private Sesion sesion;

    public String getObjetivo() {
        return objetivo;
    }

    public String getNivel() {
        return nivel;
    }

    public Membresia getMembresia() {
        return Membresia;
    }

    public PlanEntrenamiento getPlanEntrenamiento() {
        return PlanEntrenamiento;
    }

    public Sesion get() {
        return sesion;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setMembresia(Membresia Membresia) {
        this.Membresia = Membresia;
    }

    public void setPlanEntrenamiento(PlanEntrenamiento PlanEntrenamiento) {
        this.PlanEntrenamiento = PlanEntrenamiento;
    }

    public void set(Sesion sesion) {
        this.sesion = sesion;
    }

    public void linkMembresia(Membresia _Membresia) {
        if (_Membresia != null) {
            _Membresia.getCliente().add(this);
        }

        unlinkMembresia();
        setMembresia(_Membresia);
    }

    public void linkPlanEntrenamiento(PlanEntrenamiento _PlanEntrenamiento) {
        if (_PlanEntrenamiento != null) {
            _PlanEntrenamiento.unlinkCliente();
            _PlanEntrenamiento.setCliente(this);
        }

        unlinkPlanEntrenamiento();
        setPlanEntrenamiento(_PlanEntrenamiento);
    }

    public void link(Sesion _sesion) {
        if (_sesion != null) {
            _sesion.unlink();
            _sesion.set(this);
        }

        unlink();
        set(_sesion);
    }

    public void unlinkMembresia() {
        if (getMembresia() != null) {
            getMembresia().getCliente().remove(this);
            setMembresia(null);
        }
    }

    public void unlinkPlanEntrenamiento() {
        if (getPlanEntrenamiento() != null) {
            getPlanEntrenamiento().setCliente(null);
            setPlanEntrenamiento(null);
        }
    }

    public void unlink() {
//        if (get() != null) {
//            get().set(null);
//            set(null);
//        }
    }

    public void verRutina() {

    }

    public void reservarCita() {

    }
}