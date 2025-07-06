package com.soft.gymapp.dominio.usuarios;

import jakarta.persistence.Embeddable;

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