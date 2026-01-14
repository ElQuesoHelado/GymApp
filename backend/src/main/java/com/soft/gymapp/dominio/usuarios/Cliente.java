package com.soft.gymapp.dominio.usuarios;

import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.servicios.dto.ClienteDTO;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cliente")
public class Cliente extends Usuario {

    private static final Logger log = LoggerFactory.getLogger(Cliente.class);

    private String objetivo;
    private String nivel;

    @OneToOne
    private Membresia membresia;

    @OneToOne
    private PlanEntrenamiento planEntrenamiento;

    @ManyToMany(mappedBy = "clientes")
    private List<Sesion> sesiones = new ArrayList<>();

    public Cliente() {}

    public Cliente(String objetivo, String nivel, Membresia membresia, PlanEntrenamiento planEntrenamiento) {
        this.objetivo = objetivo;
        this.nivel = nivel;
        this.membresia = membresia;
        this.planEntrenamiento = planEntrenamiento;
    }

    public ClienteDTO toDTO() {
        return new ClienteDTO(
                this.getId(),
                this.getNombre(),
                this.getDni(),
                this.getEmail(),
                this.getTelefono(),
                this.getFechaNacimiento(),
                this.getObjetivo(),
                this.getNivel()
        );
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public PlanEntrenamiento getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        this.planEntrenamiento = planEntrenamiento;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void agregarSesion(Sesion sesion) {
        if (sesion != null && !sesiones.contains(sesion)) {
            sesiones.add(sesion);
        }
    }

    public void eliminarSesion(Sesion sesion) {
        sesiones.remove(sesion);
    }

    public void verRutina() {
        if (planEntrenamiento != null) {
            planEntrenamiento.verRutinas(); // Se espera que use log internamente
        } else {
            log.warn("No tiene un plan de entrenamiento asignado.");
        }
    }

    public void reservarCita(Sesion sesion) {
        if (sesion != null && !sesiones.contains(sesion)) {
            sesiones.add(sesion);
            sesion.agregarCliente(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
