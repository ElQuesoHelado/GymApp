package com.soft.gymapp.dominio.notificaciones;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepositorio
    extends JpaRepository<Notificacion, Integer> {

    List<Notificacion> findByUsuarioId(Integer id);

    List<Notificacion> findByUsuarioIdAndLeidoFalse(Integer id);

  // La interfaz JpaRepository ya proporciona todos los métodos CRUD necesarios:
  // save(), findById(), findAll(), deleteById(), etc.
}