package com.cancheros.GymApp.Dominio.Rutinas;

import com.cancheros.GymApp.Dominio.Usuarios.Cliente;
import com.cancheros.GymApp.Dominio.Usuarios.Entrenador;
import jakarta.persistence.*;

import java.util.*;
import java.time.*;

@Entity
@Table(name = "plan_entrenamiento")
public class PlanEntrenamiento {
    @Id
    private int id;
    private Date fechaInicio;
    private int duracionSemanas;

    @OneToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rutina> rutinas = new ArrayList<>();

    @ManyToOne
    private Entrenador entrenador;

    public void asignarRutina(Rutina rutina) {

    }

    public void modificarRutina(Rutina rutina) {

    }

}