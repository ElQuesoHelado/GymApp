package com.soft.gymapp.notificaciones.repositorio;

import com.soft.gymapp.notificaciones.dominio.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepositorio extends JpaRepository<Notificacion, Long> {
    
    // Busca todas las notificaciones de un usuario
    List<Notificacion> findByUsuarioId(Long usuarioId);

    // Busca las NO leídas (leido = false) de un usuario
    List<Notificacion> findByUsuarioIdAndLeidoFalse(Long usuarioId);
}