package com.cancheros.GymApp.Dominio.Usuarios;

import jakarta.persistence.Embeddable;

import java.util.*;
import java.time.*;

@Embeddable
public class CuentaUsuario {

    private String username;

    private String password;

    private String estado;

//    private Usuario Usuario;

    public void cambiarPassword() {

    }

    public void bloquearCuenta() {

    }
}