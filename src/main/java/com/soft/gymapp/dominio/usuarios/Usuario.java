package com.soft.gymapp.dominio.usuarios;

import com.soft.gymapp.dominio.notificaciones.Notificacion;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Agregamos estrategia de generación de ID para un DB real
    private int id;
    private String nombre;
    private String DNI;
    private String email;
    private String telefono;
    private Date fechaNacimiento;

    @Embedded
    private CuentaUsuario CuentaUsuario; // JPA maneja el nombre de campo automáticamente

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // Añadir configuración básica para OneToMany
    private List<Notificacion> notificaciones = new ArrayList<>();


    public Usuario() {
        // Constructor vacío para JPA
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDNI() { return DNI; }
    public void setDNI(String DNI) { this.DNI = DNI; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public CuentaUsuario getCuentaUsuario() { return CuentaUsuario; }
    public void setCuentaUsuario(CuentaUsuario CuentaUsuario) { this.CuentaUsuario = CuentaUsuario; }
    public List<Notificacion> getNotificaciones() { return notificaciones; }
    public void setNotificaciones(List<Notificacion> notificaciones) { this.notificaciones = notificaciones; }


    // Métodos de dominio
    public void iniciarSesion() {
        // Lógica de dominio para iniciar sesión (ej. actualizar último login)
    }

    public void cerrarSesion() {
        // Lógica de dominio para cerrar sesión
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public CuentaUsuario getCuentaUsuario() {
        return CuentaUsuario;
    }

    public void setCuentaUsuario(CuentaUsuario cuentaUsuario) {
        CuentaUsuario = cuentaUsuario;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }
}