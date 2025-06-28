package com.cancheros.GymApp.Dominio.Usuarios;

import java.util.*;
import java.time.*;

public class CuentaUsuario {

    private String username;

    private String password;

    private String estado;

    private Usuario Usuario;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEstado() {
        return estado;
    }

    public Usuario getUsuario() {
        return Usuario;
    }

    public Usuario get() {
        return Usuario;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUsuario(Usuario Usuario) {
        this.Usuario = Usuario;
    }

    public void set(Usuario Usuario) {
        this.Usuario = Usuario;
    }

    public void linkUsuario(Usuario _Usuario) {
        if (_Usuario != null) {
            _Usuario.unlinkCuentaUsuario();
            _Usuario.setCuentaUsuario(this);
        }

        unlinkUsuario();
        setUsuario(_Usuario);
    }

    public void link(Usuario _Usuario) {
//        if (_Usuario != null) {
//            _Usuario.unlink();
//            _Usuario.set(this);
//        }
//
//        unlink();
//        set(_Usuario);
    }

    public void unlinkUsuario() {
        if (getUsuario() != null) {
            getUsuario().setCuentaUsuario(null);
            setUsuario(null);
        }
    }

    public void unlink() {
//        if (get() != null) {
//            get().set(null);
//            set(null);
//        }
    }

    public void cambiarPassword() {

    }

    public void bloquearCuenta() {

    }
}