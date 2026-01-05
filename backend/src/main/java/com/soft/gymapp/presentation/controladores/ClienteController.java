package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.UsuarioService;
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

    public ClienteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/home")
    public UsuarioDTO home(Authentication auth) {
        return usuarioService.obtenerUsuarioLogueado(auth);
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
