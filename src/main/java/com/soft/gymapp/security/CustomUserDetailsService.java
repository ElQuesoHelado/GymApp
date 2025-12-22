package com.soft.gymapp.security;

import com.soft.gymapp.dominio.usuarios.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepository;

    public CustomUserDetailsService(UsuarioRepositorio usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCuentaUsuarioUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String role = determineRole(usuario);
        
        // DEPURACIÓN
        System.out.println("=== DEPURACIÓN loadUserByUsername ===");
        System.out.println("Username: " + usuario.getCuentaUsuario().getUsername());
        System.out.println("Password: " + usuario.getCuentaUsuario().getPassword());
        System.out.println("Role determinado: '" + role + "'");
        System.out.println("Tipo de usuario: " + usuario.getClass().getSimpleName());
        System.out.println("================================");

        return User.builder()
                .username(usuario.getCuentaUsuario().getUsername())
                .password("{noop}" + usuario.getCuentaUsuario().getPassword())
                .roles(role)
                .build();
    }

    private String determineRole(Usuario usuario) {
        if (usuario instanceof Cliente) {
            return "CLIENTE";  // Esto se convierte a "ROLE_CLIENTE"
        } else if (usuario instanceof Entrenador) {
            return "ENTRENADOR";  // Esto se convierte a "ROLE_ENTRENADOR"
        } else if (usuario instanceof Administrador) {
            return "ADMIN";  // Esto se convierte a "ROLE_ADMIN"
        }
        throw new IllegalStateException("Tipo de usuario desconocido");
    }
}