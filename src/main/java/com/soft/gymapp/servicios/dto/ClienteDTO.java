package com.soft.gymapp.servicios.dto;

import java.time.LocalDate;

public class ClienteDTO {
    private Integer id;
    private String nombre;
    private String dni;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String objetivo;
    private String nivel;

    // Constructor vacío
    public ClienteDTO() {
    }

    // Constructor completo
    public ClienteDTO(Integer id, String nombre, String dni, String email, String telefono,
                     LocalDate fechaNacimiento, String objetivo, String nivel) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.objetivo = objetivo;
        this.nivel = nivel;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
