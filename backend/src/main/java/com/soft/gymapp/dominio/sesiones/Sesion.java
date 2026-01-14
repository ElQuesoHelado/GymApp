package com.soft.gymapp.dominio.sesiones;

import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import com.soft.gymapp.servicios.dto.ClienteDTO;
import com.soft.gymapp.servicios.dto.SesionDTO;
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

    public SesionDTO toDTO() {
        // Convertir lista de Clientes a ClienteDTO
        List<ClienteDTO> clienteDTOs = this.clientes != null
                ? this.clientes.stream()
                .map(Cliente::toDTO)
                .toList()
                : new ArrayList<>();

        return new SesionDTO(
                this.id,
                this.estado.toString(),
                clienteDTOs,
                this.entrenador != null ? this.entrenador.toDTO() : null,
                this.horario != null ? this.horario.toDTO() : null,
                this.sala != null ? this.sala.toDTO() : null
        );
    }

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
}
