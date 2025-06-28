package com.cancheros.GymApp.Dominio.Membresias;

import java.util.*;
import java.time.*;

public class MetodoPago {

    private String tipo;

    private PagoMembresia pagoMenbreswia;

    public String getTipo() {
        return tipo;
    }

    public PagoMembresia getPagoMenbreswia() {
        return pagoMenbreswia;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPagoMenbreswia(PagoMembresia pagoMenbreswia) {
        this.pagoMenbreswia = pagoMenbreswia;
    }

    public void linkPagoMenbreswia(PagoMembresia _pagoMenbreswia) {
        if (_pagoMenbreswia != null) {
            _pagoMenbreswia.unlinkMetodoPago();
            _pagoMenbreswia.setMetodoPago(this);
        }

        unlinkPagoMenbreswia();
        setPagoMenbreswia(_pagoMenbreswia);
    }

    public void unlinkPagoMenbreswia() {
        if (getPagoMenbreswia() != null) {
            getPagoMenbreswia().setMetodoPago(null);
            setPagoMenbreswia(null);
        }
    }


    public void validarMetodo() {
    }

}