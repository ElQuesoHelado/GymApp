package com.soft.gymapp.sesiones.dominio;

import jakarta.persistence.*;

/**
 * Entidad que representa una sala de entrenamiento.
 */
@Entity
@Table(name = "salas")
public class Sala {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activa", nullable = false)
    private boolean activa = true;

    // Constructores
    public Sala() {}

    public Sala(String nombre, Integer capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public Sala(String nombre, Integer capacidad, String descripcion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    // Métodos de lógica de negocio
    public void desactivar() {
        this.activa = false;
    }

    public void activar() {
        this.activa = true;
    }

    public void actualizarCapacidad(Integer nuevaCapacidad) {
        if (nuevaCapacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        this.capacidad = nuevaCapacidad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
