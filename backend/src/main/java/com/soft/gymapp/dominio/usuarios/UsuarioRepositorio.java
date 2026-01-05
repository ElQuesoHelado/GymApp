package com.soft.gymapp.dominio.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCuentaUsuarioUsername(String nombre);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByDni(String dni);
}