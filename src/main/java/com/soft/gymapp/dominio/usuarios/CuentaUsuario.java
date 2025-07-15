package com.soft.gymapp.dominio.usuarios;

import jakarta.persistence.Embeddable;

@Embeddable
public class CuentaUsuario {

    private String username;
    private String password; // Aquí irá el hash de la contraseña
    private String estado;

    public CuentaUsuario() {
        // Constructor vacío requerido por JPA
    }

    public CuentaUsuario(String username, String password, String estado) {
        this.username = username;
        this.password = password;
        this.estado = estado;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void cambiarPassword() {
        // Lógica para cambiar la contraseña
    }

    public void bloquearCuenta() {
        this.estado = "BLOQUEADA";
    }
}