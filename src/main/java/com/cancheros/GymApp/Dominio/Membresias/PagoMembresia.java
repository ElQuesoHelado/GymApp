package com.cancheros.GymApp.Dominio.Membresias;

import java.util.*;
import java.time.*;

import com.cancheros.GymApp.Dominio.Membresias.MetodoPago;

public class PagoMembresia {

    private String idPago;

    private float monto;

    private Date fechaPago;

    private Membresia Menbresia;

    private MetodoPago MetodoPago;

    public String getIdPago() {
        return idPago;
    }

    public float getMonto() {
        return monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public Membresia getMenbresia() {
        return Menbresia;
    }

    public MetodoPago getMetodoPago() {
        return MetodoPago;
    }

    public Membresia get() {
        return Menbresia;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setMenbresia(Membresia Menbresia) {
        this.Menbresia = Menbresia;
    }

    public void setMetodoPago(MetodoPago MetodoPago) {
        this.MetodoPago = MetodoPago;
    }

    public void set(Membresia Menbresia) {
        this.Menbresia = Menbresia;
    }

    public void linkMenbresia(Membresia _Menbresia) {
        if (_Menbresia != null) {
            _Menbresia.getPagoMembresia().add(this);
        }

        unlinkMenbresia();
        setMenbresia(_Menbresia);
    }

    public void linkMetodoPago(MetodoPago _MetodoPago) {
        if (_MetodoPago != null) {
            _MetodoPago.unlinkPagoMenbreswia();
            _MetodoPago.setPagoMenbreswia(this);
        }

        unlinkMetodoPago();
        setMetodoPago(_MetodoPago);
    }

    public void link(Membresia _Menbresia) {
//        if (_Menbresia != null) {
//            _Menbresia.get().add(this);
//        }
//
//        unlink();
//        set(_Menbresia);
    }

    public void unlinkMenbresia() {
        if (getMenbresia() != null) {
            getMenbresia().getPagoMembresia().remove(this);
            setMenbresia(null);
        }
    }

    public void unlinkMetodoPago() {
        if (getMetodoPago() != null) {
            getMetodoPago().setPagoMenbreswia(null);
            setMetodoPago(null);
        }
    }

    public void unlink() {
        if (get() != null) {
            get().get().remove(this);
            set(null);
        }
    }


    public void procesarPago() {

    }

    public void verificarPago() {

    }
}