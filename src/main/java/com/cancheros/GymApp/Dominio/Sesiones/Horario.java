package com.cancheros.GymApp.Dominio.Sesiones;

import com.cancheros.GymApp.Dominio.Usuarios.Cliente;

import java.sql.Time;
import java.util.*;
import java.time.*;

public class Horario {
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;
    private Sesion sesion;

    public Date getFecha() {
        return fecha;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public Sesion get() {
        return sesion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public void set(Sesion sesion) {
        this.sesion = sesion;
    }

    public void linkSesion(Sesion _sesion) {
        if (_sesion != null) {
            _sesion.unlinkHorario();
            _sesion.setHorario(this);
        }

        unlinkSesion();
        setSesion(_sesion);
    }

    public void link(Sesion _sesion) {
//        if (_sesion != null) {
//            _sesion.unlink();
//            _sesion.set(this);
//        }
//
//        unlink();
//        set(_sesion);
    }


    public void unlinkSesion() {
        if (getSesion() != null) {
            getSesion().setHorario(null);
            setSesion(null);
        }
    }

    public void unlink() {
        if (get() != null) {
            get().set((Cliente) null);
            set(null);
        }
    }

    public boolean esDisponible() {

        return false;
    }

}