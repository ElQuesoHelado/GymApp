package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.PlanEntrenamientoService;
import com.soft.gymapp.servicios.UsuarioService;
import com.soft.gymapp.servicios.dto.ClienteDTO;
import com.soft.gymapp.servicios.dto.MembresiaDTO;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.servicios.dto.UsuarioDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
@PreAuthorize("hasRole('CLIENTE')")
public class ClienteController {

    private final UsuarioService usuarioService;
    private final PlanEntrenamientoService planEntrenamientoService;

    public ClienteController(UsuarioService usuarioService, PlanEntrenamientoService planEntrenamientoService) {
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

    @GetMapping("/sesiones")
    public String sesiones() {
        return "cliente/sesiones";
    }
}
