package com.soft.gymapp.dominio.notificaciones;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepositorio
    extends JpaRepository<Notificacion, Integer> {
  // La interfaz JpaRepository ya proporciona todos los métodos CRUD necesarios:
  // save(), findById(), findAll(), deleteById(), etc.
}