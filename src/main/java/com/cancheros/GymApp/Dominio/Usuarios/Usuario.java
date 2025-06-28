package com.cancheros.GymApp.Dominio.Usuarios;

import com.cancheros.GymApp.Dominio.Notificaciones.Notificacion;

import java.util.*;
import java.time.*;


public class Usuario {
    private int id;
    private String nombre;
    private String DNI;
    private String email;
    private String telefono;
    private Date fechaNacimiento;
    private CuentaUsuario CuentaUsuario;
    private Notificacion Notificacion;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public CuentaUsuario getCuentaUsuario() {
        return CuentaUsuario;
    }

    public Notificacion getNotificacion() {
        return Notificacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCuentaUsuario(CuentaUsuario CuentaUsuario) {
        this.CuentaUsuario = CuentaUsuario;
    }

    public void setNotificacion(Notificacion Notificacion) {
        this.Notificacion = Notificacion;
    }

    public void linkCuentaUsuario(CuentaUsuario _CuentaUsuario) {
        if (_CuentaUsuario != null) {
            _CuentaUsuario.unlinkUsuario();
            _CuentaUsuario.setUsuario(this);
        }

        unlinkCuentaUsuario();
        setCuentaUsuario(_CuentaUsuario);
    }

    public void linkNotificacion(Notificacion _Notificacion) {
        if (_Notificacion != null) {
            _Notificacion.unlinkUsuario();
            _Notificacion.setUsuario(this);
        }

        unlinkNotificacion();
        setNotificacion(_Notificacion);
    }

    public void unlinkCuentaUsuario() {
        if (getCuentaUsuario() != null) {
            getCuentaUsuario().setUsuario(null);
            setCuentaUsuario(null);
        }
    }

    public void unlinkNotificacion() {
        if (getNotificacion() != null) {
            getNotificacion().setUsuario(null);
            setNotificacion(null);
        }
    }

    public void iniciarSesion() {

    }

    public void cerrarSesion() {

    }
}