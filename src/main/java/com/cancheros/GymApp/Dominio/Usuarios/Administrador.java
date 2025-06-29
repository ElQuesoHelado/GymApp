package com.cancheros.GymApp.Dominio.Usuarios;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.*;
import java.time.*;

@Entity
@Table(name = "administrador")
public class Administrador extends Usuario {
    private String rol;

    public void gestionarUsuarios() {

    }

    public void verReportes() {

    }
}