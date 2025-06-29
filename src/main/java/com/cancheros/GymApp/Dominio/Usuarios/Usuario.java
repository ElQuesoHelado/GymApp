package com.cancheros.GymApp.Dominio.Usuarios;

import com.cancheros.GymApp.Dominio.Notificaciones.Notificacion;
import jakarta.persistence.*;

import java.util.*;
import java.time.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    private int id;
    private String nombre;
    private String DNI;
    private String email;
    private String telefono;
    private Date fechaNacimiento;

    @Embedded
    private CuentaUsuario CuentaUsuario;

    @OneToMany
    private List<Notificacion> notificaciones = new ArrayList<>();

    public void iniciarSesion() {

    }

    public void cerrarSesion() {

    }
}