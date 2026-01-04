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
        return "cliente/dashboard";
    }

    @GetMapping("/plan")
    public String plan() {
        return "cliente/plan";
    }

    @GetMapping("/membresia")
    public String membresia() {
        return "cliente/membresia";
    }

    @GetMapping("/sesiones")
    public String sesiones() {
        return "cliente/sesiones";
    }
}
