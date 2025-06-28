package com.cancheros.GymApp.Dominio.Membresias;

import java.util.*;
import java.time.*;


import com.cancheros.GymApp.Dominio.Usuarios.Cliente;

public class Membresia {
    private String idMembresia;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private Set<Cliente> cliente = new HashSet<>();
    private Set<PagoMembresia> PagoMembresia = new HashSet<>();
    private TipoMembresia tipomenbresia;

    public String getIdMembresia() {
        return idMembresia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public Set<Cliente> getCliente() {
        return cliente;
    }

    public Set<PagoMembresia> getPagoMembresia() {
        return PagoMembresia;
    }

    public TipoMembresia getTipomenbresia() {
        return tipomenbresia;
    }

    public Set<Cliente> get() {
        return cliente;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTipomenbresia(TipoMembresia tipomenbresia) {
        this.tipomenbresia = tipomenbresia;
    }

    public void linkCliente(Cliente _cliente) {
        if (_cliente != null) {
            _cliente.unlinkMembresia();
            _cliente.setMembresia(this);
            getCliente().add(_cliente);
        }
    }

    public void linkPagoMembresia(PagoMembresia _PagoMembresia) {
        if (_PagoMembresia != null) {
            _PagoMembresia.unlinkMenbresia();
            _PagoMembresia.setMenbresia(this);
            getPagoMembresia().add(_PagoMembresia);
        }
    }

    public void linkTipomenbresia(TipoMembresia _tipomenbresia) {
        if (_tipomenbresia != null) {
            _tipomenbresia.unlinkMembresia();
            _tipomenbresia.setMembresia(this);
        }

        unlinkTipomenbresia();
        setTipomenbresia(_tipomenbresia);
    }

    public void link(Cliente _cliente) {
//        if (_cliente != null) {
//            _cliente.unlink();
//            _cliente.set(this);
//            get().add(_cliente);
//        }
    }

    public void unlinkCliente(Cliente _cliente) {
        if (_cliente != null) {
            _cliente.setMembresia(null);
            getCliente().remove(_cliente);
        }
    }

    public void unlinkCliente(Cliente _cliente, Iterator<Cliente> it) {
        if (_cliente != null) {
            _cliente.setMembresia(null);
            it.remove();
        }
    }

    public void unlinkPagoMembresia(PagoMembresia _PagoMembresia) {
        if (_PagoMembresia != null) {
            _PagoMembresia.setMenbresia(null);
            getPagoMembresia().remove(_PagoMembresia);
        }
    }

    public void unlinkPagoMembresia(PagoMembresia _PagoMembresia, Iterator<PagoMembresia> it) {
        if (_PagoMembresia != null) {
            _PagoMembresia.setMenbresia(null);
            it.remove();
        }
    }

    public void unlinkTipomenbresia() {
        if (getTipomenbresia() != null) {
            getTipomenbresia().setMembresia(null);
            setTipomenbresia(null);
        }
    }

    public void unlink(Cliente _cliente) {
        if (_cliente != null) {
            _cliente.set(null);
            get().remove(_cliente);
        }
    }

    public void unlink(Cliente _cliente, Iterator<Cliente> it) {
        if (_cliente != null) {
            _cliente.set(null);
            it.remove();
        }
    }

    public void activar() {

    }

    public void cancelar() {

    }

    public boolean esActiva() {

        return false;
    }

}