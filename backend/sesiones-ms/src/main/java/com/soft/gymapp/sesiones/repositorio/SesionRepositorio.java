package com.soft.gymapp.sesiones.repositorio;

import com.soft.gymapp.sesiones.dominio.Sesion;
import com.soft.gymapp.sesiones.dominio.EstadoSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la entidad Sesion.
 */
@Repository
public interface SesionRepositorio extends JpaRepository<Sesion, Long> {

    List<Sesion> findByIdEntrenadorOrderByHorarioFechaDesc(Long idEntrenador);

    List<Sesion> findByIdPlanEntrenamientoOrderByHorarioFechaDesc(Long idPlanEntrenamiento);

    List<Sesion> findByEstadoOrderByHorarioFechaDesc(EstadoSesion estado);

    List<Sesion> findByIdSalaOrderByHorarioFechaDesc(Long idSala);

    List<Sesion> findByIdEntrenadorAndEstadoOrderByHorarioFechaDesc(Long idEntrenador, EstadoSesion estado);

    @Query("SELECT s FROM Sesion s WHERE s.horario.fecha = :fecha ORDER BY s.horario.horaInicio ASC")
    List<Sesion> findSesionesPorFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT s FROM Sesion s WHERE s.idSala = :idSala AND s.horario.fecha = :fecha " +
           "ORDER BY s.horario.horaInicio ASC")
    List<Sesion> findSesionesPorSalaYFecha(@Param("idSala") Long idSala, @Param("fecha") LocalDate fecha);

    long countByEstado(EstadoSesion estado);

    long countByIdEntrenador(Long idEntrenador);
}
