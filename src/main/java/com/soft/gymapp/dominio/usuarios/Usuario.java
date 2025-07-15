package com.soft.gymapp.dominio.usuarios;

import com.soft.gymapp.dominio.notificaciones.Notificacion;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    private int id;
    private String nombre;
    private String DNI;
    private String email;
    private String telefono;
    private Date fechaNacimiento;

    @Embedded
    private CuentaUsuario CuentaUsuario;

    @OneToMany
    private List<Notificacion> notificaciones = new ArrayList<>();

    public void iniciarSesion() {

    }

    public void cerrarSesion() {

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