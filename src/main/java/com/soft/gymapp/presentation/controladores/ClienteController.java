package com.soft.gymapp.presentation.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
@PreAuthorize("hasRole('CLIENTE')")
public class ClienteController {
    @GetMapping("/dashboard")
    public String dashboard() {
        // ...
        return "cliente/dashboard.html";
    }

    @GetMapping("/plan")
    public String plan() {
        //...
        return "cliente/plan.html";
    }

    @GetMapping("/membresia")
    public String membresia() {
        //...
        return "cliente/membresia.html";
    }

    @GetMapping("/sesiones")
    public String sesiones() {
        //...
        return "cliente/sesiones.html";
    }
}
