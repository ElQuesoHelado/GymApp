package com.soft.gymapp.servicios.dto;

import java.time.LocalDate;

public class EntrenadorDTO {
    private Integer id;
    private String nombre;
    private String dni;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String especialidad;
    private String certificaciones;

    // Constructor vacío
    public EntrenadorDTO() {
    }

    // Constructor completo
    public EntrenadorDTO(Integer id, String nombre, String dni, String email, String telefono, 
                        LocalDate fechaNacimiento, String especialidad, String certificaciones) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.especialidad = especialidad;
        this.certificaciones = certificaciones;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(String certificaciones) {
        this.certificaciones = certificaciones;
    }
}
