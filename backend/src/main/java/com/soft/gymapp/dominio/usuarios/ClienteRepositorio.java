package com.soft.gymapp.dominio.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByCuentaUsuarioUsername(String nombre);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByDni(String dni);
}