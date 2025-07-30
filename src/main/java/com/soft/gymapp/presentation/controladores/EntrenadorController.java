package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entrenador")
@PreAuthorize("hasRole('ENTRENADOR')")
public class EntrenadorController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/dashboard")
    public String dashboard() {
        // ...
        return "entrenador/dashboard.html";
    }

    @GetMapping("/rutinas")
    public String rutinas() {
        //...
        return "entrenador/rutinas.html";
    }

    @GetMapping("/planes")
    public String planes() {
        //...
        return "entrenador/planes.html";
    }

    @GetMapping("/clientes")
    public String clientes() {
        //...
        return "entrenador/clientes.html";
    }

    @GetMapping("/sesiones")
    public String sesiones() {
        //...
        return "entrenador/sesiones.html";
    }
}
