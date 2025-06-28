package com.cancheros.GymApp.Dominio.Rutinas;

import com.cancheros.GymApp.Dominio.Usuarios.Cliente;

import java.util.*;
import java.time.*;

public class PlanEntrenamiento {
    private int id;
    private Date fechaInicio;
    private int duracionSemanas;
    private Cliente cliente;
    private Rutina Rutina;

    public int getId() {
        return id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public int getDuracionSemanas() {
        return duracionSemanas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Rutina getRutina() {
        return Rutina;
    }

    public Cliente get() {
        return cliente;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setDuracionSemanas(int duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setRutina(Rutina Rutina) {
        this.Rutina = Rutina;
    }

    public void set(Cliente cliente) {
        this.cliente = cliente;
    }

    public void linkCliente(Cliente _cliente) {
        if (_cliente != null) {
            _cliente.unlinkPlanEntrenamiento();
            _cliente.setPlanEntrenamiento(this);
        }

        unlinkCliente();
        setCliente(_cliente);
    }

    public void linkRutina(Rutina _Rutina) {
        if (_Rutina != null) {
            _Rutina.unlinkPlanEntrenamiento();
            _Rutina.setPlanEntrenamiento(this);
        }

        unlinkRutina();
        setRutina(_Rutina);
    }

    public void link(Cliente _cliente) {
//        if (_cliente != null) {
//            _cliente.unlink();
//            _cliente.set(this);
//        }
//
//        unlink();
//        set(_cliente);
    }

    public void unlinkCliente() {
        if (getCliente() != null) {
            getCliente().setPlanEntrenamiento(null);
            setCliente(null);
        }
    }

    public void unlinkRutina() {
        if (getRutina() != null) {
            getRutina().setPlanEntrenamiento(null);
            setRutina(null);
        }
    }

    public void unlink() {
        if (get() != null) {
            get().set(null);
            set(null);
        }
    }

    public void asignarRutina(Rutina rutina) {

    }

    public void modificarRutina(Rutina rutina) {

    }

}