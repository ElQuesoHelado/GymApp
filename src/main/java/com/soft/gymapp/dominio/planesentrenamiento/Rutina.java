package com.soft.gymapp.dominio.planesentrenamiento;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rutina")
public class Rutina {

    private static final Logger logger = LoggerFactory.getLogger(Rutina.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String objetivo;

    @ManyToMany(mappedBy = "rutinas")
    private List<PlanEntrenamiento> planEntrenamiento;

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejercicio> ejercicios = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Rutina() {
    }

    public Rutina(String nombre, String objetivo) {
        this.nombre = nombre;
        this.objetivo = objetivo;
    }

    public void agregarEjercicio(Ejercicio ejercicio) {
        if (ejercicio == null) {
            logger.warn("Intento de agregar un ejercicio nulo a la rutina.");
            return;
        }
        ejercicios.add(ejercicio);
        ejercicio.setRutina(this); // importante si hay relación bidireccional
        logger.info("Ejercicio agregado a la rutina: {}", ejercicio.getNombre());
    }

    public void quitarEjercicio(Ejercicio ejercicio) {
        if (ejercicio == null) {
            logger.warn("Intento de quitar un ejercicio nulo de la rutina.");
            return;
        }
        ejercicios.remove(ejercicio);
        ejercicio.setRutina(null); // eliminar referencia si aplica
        logger.info("Ejercicio eliminado de la rutina: {}", ejercicio.getNombre());
    }

    public void mostrarRutina() {
        logger.info("Rutina: {} - Objetivo: {}", nombre, objetivo);
        if (ejercicios.isEmpty()) {
            logger.info("La rutina no tiene ejercicios.");
        } else {
            for (Ejercicio e : ejercicios) {
                logger.info(" - Ejercicio: {}", e.getNombre());
            }
        }
    }

    /**
     * Asigna la rutina a una membresía si no está vencida.
     * @param membresia Membresia a la que se asigna la rutina
     * @return true si se asigna, false si la membresía está vencida
     */
    public boolean asignarRutina(com.soft.gymapp.dominio.membresias.Membresia membresia) {
        if (membresia == null || membresia.isVencida()) {
            logger.warn("No se puede asignar rutina: membresía vencida o nula.");
            return false;
        }
        // Aquí iría la lógica de asignación real (por ejemplo, agregar la rutina a la membresía)
        logger.info("Rutina asignada correctamente a la membresía.");
        return true;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public List<PlanEntrenamiento> getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(List<PlanEntrenamiento> planEntrenamiento) {
        this.planEntrenamiento = planEntrenamiento;
    }
}
