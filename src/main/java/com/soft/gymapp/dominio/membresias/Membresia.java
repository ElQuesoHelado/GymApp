package com.soft.gymapp.dominio.membresias;

import com.soft.gymapp.dominio.usuarios.Cliente;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "membresia")
public class Membresia {
  @Id private Integer id;

  private Date fechaInicio;
  private Date fechaFin;

  @OneToMany(mappedBy = "membresia")
  private List<PagoMembresia> pagosMembresia = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EstadoMembresia estado;

  @Embedded private TipoMembresia tipo;

  @OneToOne private Cliente cliente;

  public void activar() { estado = EstadoMembresia.ACTIVADA; }

  public void cancelar() { estado = EstadoMembresia.CANCELADA; }

  public boolean esActiva() { return estado == EstadoMembresia.ACTIVADA; }

  public Integer getId() { return id; }

  public void setId(Integer idMembresia) { this.id = idMembresia; }

  public Date getFechaInicio() { return fechaInicio; }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() { return fechaFin; }

  public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

  public List<PagoMembresia> getPagosMembresia() { return pagosMembresia; }

  public void setPagosMembresia(List<PagoMembresia> pagosMembresia) {
    this.pagosMembresia = pagosMembresia;
  }

  public EstadoMembresia getEstado() { return estado; }

  public void setEstado(EstadoMembresia estado) { this.estado = estado; }

  public TipoMembresia getTipo() { return tipo; }

  public void setTipo(TipoMembresia tipo) { this.tipo = tipo; }

  public Cliente getCliente() { return cliente; }

  public void setCliente(Cliente cliente) { this.cliente = cliente; }
  public boolean estaActiva() {
    return this.estado == EstadoMembresia.ACTIVADA;
  }

  public boolean estaVencida() {
    if (this.fechaFin == null)
      return false;
    return this.fechaFin.before(new Date()) &&
        this.estado == EstadoMembresia.ACTIVADA;
  }

  public boolean isVencida() {
    return estaVencida();
  }

  /**
   * Método para pruebas: fuerza el estado de vencida de la membresía.
   */
  public void setVencida(boolean vencida) {
    if (vencida) {
      this.fechaFin = new Date(System.currentTimeMillis() - 1000); // fecha pasada
      this.estado = EstadoMembresia.ACTIVADA;
    } else {
      this.fechaFin = new Date(System.currentTimeMillis() + 100000000); // fecha futura
      this.estado = EstadoMembresia.ACTIVADA;
    }
  }
}