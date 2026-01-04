package com.soft.gymapp.dominio.usuarios;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class CuentaUsuario {
    private static final int LONGITUD_MINIMA_PASSWORD = 6;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private EstadoCuentaUsuario estado;

    public CuentaUsuario(String username, String password, EstadoCuentaUsuario estado) {
        this.username = username;
        this.password = password;
        this.estado = estado;
    }

    public CuentaUsuario() {

    }

    /*
     * Nueva contrasenia debe cumplir requisitos de longitud y seguridad
     */
    private void validarNuevoPassword(String nuevoPassword) {
        if (nuevoPassword.length() < LONGITUD_MINIMA_PASSWORD) {
            throw new IllegalArgumentException("Contrasenia muy corta");
        }

        if (nuevoPassword.equals(password)) {
            throw new IllegalArgumentException("Contrasenia identica a anterior");
        }
    }

    private void validarPassword(String password) {
        if (!password.equals(this.password)) {
            throw new SecurityException("Credenciales invalidas");
        }
    }

    public void cambiarPassword(String nuevoPassword, String actualPassword) {
        validarPassword(actualPassword);
        validarNuevoPassword(nuevoPassword);
        password = nuevoPassword;
    }

    public void bloquearCuenta() {
        this.estado = EstadoCuentaUsuario.BLOQUEADA;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EstadoCuentaUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuentaUsuario estado) {
        this.estado = estado;
    }

    public String getPassword() {
        return password;
    }
}