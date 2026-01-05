package com.soft.gymapp.sesiones.dominio;

/**
 * Enumeración que representa los estados posibles de una sesión.
 */
public enum EstadoSesion {
    SIN_EMPEZAR("Sesión no iniciada"),
    EN_PROGRESO("Sesión en curso"),
    TERMINADA("Sesión finalizada"),
    CANCELADA("Sesión cancelada");

    private final String descripcion;

    EstadoSesion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
