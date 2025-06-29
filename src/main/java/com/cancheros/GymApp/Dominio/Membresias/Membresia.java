package com.cancheros.GymApp.Dominio.Membresias;

import java.util.*;
import java.time.*;

import com.cancheros.GymApp.Dominio.Rutinas.Ejercicio;
import com.cancheros.GymApp.Dominio.Usuarios.Cliente;
import jakarta.persistence.*;

@Entity
@Table(name = "membresia")
public class Membresia {
    @Id
    private Integer idMembresia;

    private Date fechaInicio;
    private Date fechaFin;

    @OneToMany
    private List<PagoMembresia> pagosMembresia = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMembresia estado;

    @Embedded
    private TipoMembresia tipo;

    @OneToOne
    private Cliente cliente;


    public void activar() {

    }

    public void cancelar() {

    }

    public boolean esActiva() {
        return false;
    }

}