package com.cancheros.GymApp.Dominio.Usuarios;

import com.cancheros.GymApp.Dominio.Rutinas.Rutina;
import com.cancheros.GymApp.Dominio.Sesiones.Sesion;

import java.util.*;
import java.time.*;

public class Entrenador extends Usuario {

    private String especialidad;
    private String certificaciones;
    private Rutina Rutina;
    private Sesion Sesion;

    public String getEspecialidad() {
        return especialidad;
    }

    public String getCertificaciones() {
        return certificaciones;
    }

    public Rutina getRutina() {
        return Rutina;
    }

    public Sesion getSesion() {
        return Sesion;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setCertificaciones(String certificaciones) {
        this.certificaciones = certificaciones;
    }

    public void setRutina(Rutina Rutina) {
        this.Rutina = Rutina;
    }

    public void setSesion(Sesion Sesion) {
        this.Sesion = Sesion;
    }

    public void linkRutina(Rutina _Rutina) {
        if (_Rutina != null) {
            _Rutina.unlinkEntrenador();
            _Rutina.setEntrenador(this);
        }

        unlinkRutina();
        setRutina(_Rutina);
    }

    public void linkSesion(Sesion _Sesion) {
        if (_Sesion != null) {
            _Sesion.unlinkEntrenador();
            _Sesion.setEntrenador(this);
        }

        unlinkSesion();
        setSesion(_Sesion);
    }

    public void unlinkRutina() {
        if (getRutina() != null) {
            getRutina().setEntrenador(null);
            setRutina(null);
        }
    }

    public void unlinkSesion() {
        if (getSesion() != null) {
            getSesion().setEntrenador(null);
            setSesion(null);
        }
    }

    public void crearRutina() {

    }

    public void verClientes() {

    }
}