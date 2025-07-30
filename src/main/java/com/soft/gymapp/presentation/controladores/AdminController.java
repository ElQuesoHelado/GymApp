package com.soft.gymapp.presentation.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @GetMapping("/dashboard")
    public String dashboard() {
        // ...
        return "admin/dashboard.html";
    }

    @GetMapping("/crear_usuario")
    public String crearUsuario() {
        //...
        return "admin/crear_usuario.html";
    }

    @GetMapping("/reporte_ventas")
    public String reporteVentas() {
        //...
        return "admin/reporte_ventas.html";
    }

    @GetMapping("/usuarios")
    public String usuarios() {
        //...
        return "admin/usuarios.html";
    }
}