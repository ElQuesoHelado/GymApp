package com.soft.gymapp.security;

import com.soft.gymapp.dominio.usuarios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCuentaUsuarioUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String role = determineRole(usuario);

        return User.builder()
                .username(usuario.getCuentaUsuario().getUsername())
                .password("{noop}" + usuario.getCuentaUsuario().getPassword())
                .roles(role)
                .build();
    }

    private String determineRole(Usuario usuario) {
        if (usuario instanceof Cliente) {
            return "CLIENTE";
        } else if (usuario instanceof Entrenador) {
            return "ENTRENADOR";
        } else if (usuario instanceof Administrador) {
            return "ADMIN";
        }
        throw new IllegalStateException("Tipo de usuario desconocido");
    }
}