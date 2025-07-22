package com.soft.gymapp.dominio.notificaciones;

import org.springframework.data.jpa.repository.JpaRepository;
import com.soft.gymapp.dominio.notificaciones.Notificacion;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para acceder a las notificaciones del sistema.
 * Usa JPA para operaciones CRUD automáticas.
 */
public interface NotificacionRepositorio extends JpaRepository<Notificacion, Integer> {

    /**
     * Encuentra todas las notificaciones enviadas a un usuario por su ID.
     *
     * @param idUsuario el ID del usuario
     * @return lista de notificaciones
     */
    List<Notificacion> findByUsuarioId(Integer idUsuario);

    /**
     * Busca una notificación por su título exacto.
     *
     * @param titulo el título de la notificación
     * @return notificación encontrada, si existe
     */
    Optional<Notificacion> findByTitulo(String titulo);
}
