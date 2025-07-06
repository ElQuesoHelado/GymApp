package com.soft.gymapp.dominio.usuarios;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador extends Usuario {
    private String rol;

    public void gestionarUsuarios() {

    }

    public void verReportes() {

    }
}