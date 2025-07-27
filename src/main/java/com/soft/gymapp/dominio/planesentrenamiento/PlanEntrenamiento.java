package com.soft.gymapp.dominio.planesentrenamiento;

import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "plan_entrenamiento")
public class PlanEntrenamiento {
    @Id
    private int id;
    private LocalDate fechaInicio;
    private int duracionSemanas;

    @OneToOne
    private Cliente cliente;

//    @OneToMany(mappedBy = "planEntrenamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @ManyToMany
    @JoinTable(
            name = "plan_entrenamiento_rutina",
            joinColumns = @JoinColumn(name = "plan_entrenamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "rutina_id")
    )
    private List<Rutina> rutinas = new ArrayList<>();

    @ManyToOne
    private Entrenador entrenador;

    public void asignarRutina(Rutina rutina) {
        this.rutinas.add(rutina);
    }

    public void modificarRutina(Rutina rutina) {
        // Se implementara en el futuro
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
}