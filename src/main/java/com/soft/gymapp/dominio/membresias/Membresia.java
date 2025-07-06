package com.soft.gymapp.dominio.membresias;

import java.util.*;

import com.soft.gymapp.dominio.usuarios.Cliente;
import jakarta.persistence.*;

@Entity
@Table(name = "membresia")
public class Membresia {
    @Id
    private Integer idMembresia;

    private Date fechaInicio;
    private Date fechaFin;

    @OneToMany
    private List<PagoMembresia> pagosMembresia = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMembresia estado;

    @Embedded
    private TipoMembresia tipo;

    @OneToOne
    private Cliente cliente;


    public void activar() {
        estado = EstadoMembresia.ACTIVADA;
    }

    public void cancelar() {
        estado = EstadoMembresia.CANCELADA;
    }

    public boolean esActiva() {
        return estado == EstadoMembresia.ACTIVADA;
    }

    public Integer getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(Integer idMembresia) {
        this.idMembresia = idMembresia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<PagoMembresia> getPagosMembresia() {
        return pagosMembresia;
    }

    public void setPagosMembresia(List<PagoMembresia> pagosMembresia) {
        this.pagosMembresia = pagosMembresia;
    }

    public EstadoMembresia getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresia estado) {
        this.estado = estado;
    }

    public TipoMembresia getTipo() {
        return tipo;
    }

    public void setTipo(TipoMembresia tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}