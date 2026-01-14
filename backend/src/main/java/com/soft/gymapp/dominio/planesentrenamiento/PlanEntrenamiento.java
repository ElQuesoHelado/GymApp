package com.soft.gymapp.dominio.planesentrenamiento;

import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "plan_entrenamiento")
public class PlanEntrenamiento {

    private static final Logger logger = LoggerFactory.getLogger(PlanEntrenamiento.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate fechaInicio;
    private int duracionSemanas;

    @OneToOne
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "plan_entrenamiento_rutina",
        joinColumns = @JoinColumn(name = "plan_entrenamiento_id"),
        inverseJoinColumns = @JoinColumn(name = "rutina_id")
    )
    private List<Rutina> rutinas = new ArrayList<>();

    @ManyToOne
    private Entrenador entrenador;

    public PlanEntrenamiento() {
        // Constructor por defecto requerido por JPA
    }

    public PlanEntrenamiento(int id, LocalDate fechaInicio, int duracionSemanas, Cliente cliente, Entrenador entrenador) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.duracionSemanas = duracionSemanas;
        this.cliente = cliente;
        this.entrenador = entrenador;
    }

    public void asignarRutina(Rutina rutina) {
        if (rutina == null) {
            logger.error("No se puede asignar una rutina nula.");
            return;
        }
        rutinas.add(rutina);
        logger.info("Rutina '{}' asignada al plan de entrenamiento con ID {}", rutina.getNombre(), this.id);
    }

    public void modificarRutina(Rutina rutina) {
        if (rutina == null || rutina.getId() == 0) {
            logger.error("Rutina inválida para modificación.");
            return;
        }

        for (int i = 0; i < rutinas.size(); i++) {
            if (rutinas.get(i).getId() == rutina.getId()) {
                rutinas.set(i, rutina);
                logger.info("Rutina con ID {} modificada exitosamente en el plan de entrenamiento con ID {}", rutina.getId(), this.id);
                return;
            }
        }

        logger.error("No se encontró la rutina con ID {} para modificar.", rutina.getId());
    }

    public void verRutinas() {
        if (rutinas.isEmpty()) {
            logger.info("Este plan no tiene rutinas asignadas.");
        } else {
            logger.info("Rutinas del plan de entrenamiento:");
            for (Rutina rutina : rutinas) {
                logger.info("- {}", rutina.getNombre());
            }
        }
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDuracionSemanas() {
        return duracionSemanas;
    }

    public void setDuracionSemanas(int duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Rutina> getRutinas() {
        return this.rutinas;
    }

    public void setRutinas(List<Rutina> rutinas) {
        if (rutinas == null) {
            this.rutinas = new ArrayList<>();
        } else {
            this.rutinas = new ArrayList<>(rutinas);
        }
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }
}
