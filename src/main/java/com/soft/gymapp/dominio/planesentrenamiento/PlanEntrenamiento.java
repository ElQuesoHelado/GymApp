package com.soft.gymapp.dominio.planesentrenamiento;

import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "plan_entrenamiento")
public class PlanEntrenamiento {
    @Id
    private int id;
    private Date fechaInicio;
    private int duracionSemanas;

    @OneToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "planEntrenamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rutina> rutinas = new ArrayList<>();

    @ManyToOne
    private Entrenador entrenador;

    public void asignarRutina(Rutina rutina) {
        this.rutinas.add(rutina);
    }

    public void modificarRutina(Rutina rutina) {
        // Se implementara en el futuro
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDuracionSemanas() {
        return duracionSemanas;
    }

    public void setDuracionSemanas(int duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }
}