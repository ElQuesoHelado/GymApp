package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.PlanEntrenamientoService;
import com.soft.gymapp.servicios.SesionService;
import com.soft.gymapp.servicios.UsuarioService;
import com.soft.gymapp.servicios.dto.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
@PreAuthorize("hasRole('CLIENTE')")
public class ClienteController {

    private final UsuarioService usuarioService;
    private final PlanEntrenamientoService planEntrenamientoService;

    public ClienteController(UsuarioService usuarioService, PlanEntrenamientoService planEntrenamientoService, SesionService sesionService) {
        this.usuarioService = usuarioService;
        this.planEntrenamientoService = planEntrenamientoService;
    }

    @GetMapping("/home")
    public ClienteDTO home() {
        return usuarioService.obtenerClienteLogueado();
    }

    @GetMapping("/plan")
    public PlanEntrenamientoDTO plan() {
        return planEntrenamientoService.getPlanEntrenamientoPorClienteId();
    }

    @GetMapping("/membresia")
    public MembresiaDTO membresia() {
        return usuarioService.obtenerMembresia();
    }
}
