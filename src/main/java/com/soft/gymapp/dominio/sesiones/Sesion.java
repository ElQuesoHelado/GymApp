package com.soft.gymapp.dominio.sesiones;

import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sesion")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSesion estado;

    @ManyToMany
    @JoinTable(
        name = "cliente_sesion",
        joinColumns = @JoinColumn(name = "sesion_id"),
        inverseJoinColumns = @JoinColumn(name = "cliente_id")
    )
    private List<Cliente> clientes = new ArrayList<>();

    @ManyToOne
    private Entrenador entrenador;

    @Embedded
    private Horario horario;

    @ManyToOne
    private Sala sala;

    public void confirmar() {
        this.estado = EstadoSesion.EN_PROGRESO;
    }

    public void cancelar() {
        this.estado = EstadoSesion.TERMINADA;
    }

    public void reprogramar(Horario nuevoHorario) {
        this.horario = nuevoHorario;
        this.estado = EstadoSesion.SIN_EMPEZAR;
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente != null && !clientes.contains(cliente)) {
            clientes.add(cliente);
            cliente.agregarSesion(this); // relación bidireccional
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public EstadoSesion getEstado() {
        return estado;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public Horario getHorario() {
        return horario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void setEstado(EstadoSesion estado) {
        this.estado = estado;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }
}
