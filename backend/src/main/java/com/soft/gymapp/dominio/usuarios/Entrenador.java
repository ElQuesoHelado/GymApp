package com.soft.gymapp.dominio.usuarios;

import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.servicios.dto.EntrenadorDTO;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Entity
@Table(name = "entrenador")
public class Entrenador extends Usuario {
    private static final Logger logger = LoggerFactory.getLogger(Entrenador.class);

    private String especialidad;
    private String certificaciones;

    @OneToMany(mappedBy = "entrenador")
    private List<Sesion> sesiones = new ArrayList<>();

    @OneToMany(mappedBy = "entrenador")
    private List<PlanEntrenamiento> planes = new ArrayList<>();

    public Entrenador() {
        // Constructor vacío requerido por JPA
    }

    public EntrenadorDTO toDTO() {
        return new EntrenadorDTO(
                this.getId(),
                this.getNombre(),
                this.getDni(),
                this.getEmail(),
                this.getTelefono(),
                this.getFechaNacimiento(),
                this.especialidad,
                this.certificaciones
        );
    }

    public void crearRutina(String nombre, String objetivo, PlanEntrenamiento plan) {
        if (nombre == null || nombre.isEmpty()) {
            logger.error("El nombre de la rutina no puede estar vacío.");
            return;
        }
        if (objetivo == null || objetivo.isEmpty()) {
            logger.error("El objetivo de la rutina no puede estar vacío.");
            return;
        }
        if (plan == null) {
            logger.error("El plan de entrenamiento no puede ser nulo.");
            return;
        }

        Rutina rutina = new Rutina(nombre, objetivo);
        plan.asignarRutina(rutina);
        logger.info("Rutina '{}' creada y asignada al plan de entrenamiento por el entrenador {}", nombre, this.getNombre());
    }

    public void verClientes() {
        if (sesiones.isEmpty()) {
            logger.info("El entrenador {} no tiene sesiones asignadas.", this.getNombre());
            return;
        }

        Set<Cliente> clientesUnicos = new HashSet<>();
        for (Sesion sesion : sesiones) {
            clientesUnicos.addAll(sesion.getClientes());
        }

        if (clientesUnicos.isEmpty()) {
            logger.info("El entrenador {} no tiene clientes asignados.", this.getNombre());
        } else {
            logger.info("Clientes del entrenador {}:", this.getNombre());
            for (Cliente cliente : clientesUnicos) {
                logger.info("- {} (DNI: {})", cliente.getNombre(), cliente.getDni());
            }
        }
    }

    // Getters y Setters
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(String certificaciones) {
        this.certificaciones = certificaciones;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

    public List<PlanEntrenamiento> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlanEntrenamiento> planes) {
        this.planes = planes;
    }
}