package com.soft.gymapp.dominio.membresias;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name = "pagos_membresia")
public class PagoMembresia {
    @Id
    private String id;

    private float monto;

    private Date fechaPago;

    @ManyToOne
    private Membresia membresia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago MetodoPago;

    public String getId() {
        return id;
    }

    public float getMonto() {
        return monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public MetodoPago getMetodoPago() {
        return MetodoPago;
    }

    public Membresia get() {
        return membresia;
    }

    public void setId(String idPago) {
        this.id = idPago;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setMembresia(Membresia Menbresia) {
        this.membresia = Menbresia;
    }

    public void setMetodoPago(MetodoPago MetodoPago) {
        this.MetodoPago = MetodoPago;
    }

    public void set(Membresia Menbresia) {
        this.membresia = Menbresia;
    }

    public void procesarPago() {

    }

    public void verificarPago() {

    }
}