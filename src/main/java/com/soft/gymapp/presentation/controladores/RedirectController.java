package com.soft.gymapp.presentation.controladores;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("/redirect-por-rol")
    public String redireccionarPorRol(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        // Redireccionar según el rol
        return switch (role) {
            case "ROLE_ADMIN" -> "redirect:/admin/dashboard";
            case "ROLE_ENTRENADOR" -> "redirect:/entrenador/dashboard";
            case "ROLE_CLIENTE" -> "redirect:/cliente/dashboard";
            default -> "redirect:/login?error=unknown-role";
        };
    }
}