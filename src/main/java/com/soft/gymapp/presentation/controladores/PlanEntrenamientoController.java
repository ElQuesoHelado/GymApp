package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.PlanEntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class PlanEntrenamientoController {

    final PlanEntrenamientoService planEntrenamientoService;

    @Autowired
    public PlanEntrenamientoController(PlanEntrenamientoService planEntrenamientoService) {
        this.planEntrenamientoService = planEntrenamientoService;
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/usuarios/cliente/planes_entrenamiento")
    public String home(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();


        model.addAttribute("username", authentication.getDetails().toString());
        model.addAttribute("slogan", "Caratula inicial para app de gestión de gimnasios, esto desde el controlador");
        model.addAttribute("fecha", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "planes_entrenamiento";
    }


    public void crearRutina() {

    }

    public void asignarRutinaACliente() {

    }

    public void modificarRutina() {

    }
}