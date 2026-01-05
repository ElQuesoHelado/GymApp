package com.soft.gymapp.sesiones.servicio;

import com.soft.gymapp.sesiones.dominio.EstadoSesion;
import com.soft.gymapp.sesiones.dominio.Horario;
import com.soft.gymapp.sesiones.dto.CrearSesionDTO;
import com.soft.gymapp.sesiones.dto.SesionDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de sesiones.
 */
public interface SesionServicio {

    SesionDTO crearSesion(CrearSesionDTO crearDTO);

    Optional<SesionDTO> obtenerPorId(Long id);

    List<SesionDTO> obtenerPorEntrenador(Long idEntrenador);

    List<SesionDTO> obtenerPorPlan(Long idPlanEntrenamiento);

    List<SesionDTO> obtenerPorEstado(EstadoSesion estado);

    List<SesionDTO> obtenerPorSala(Long idSala);

    List<SesionDTO> obtenerPorFecha(LocalDate fecha);

    List<SesionDTO> obtenerPorSalaYFecha(Long idSala, LocalDate fecha);

    Optional<SesionDTO> confirmarSesion(Long id);

    Optional<SesionDTO> cancelarSesion(Long id);

    Optional<SesionDTO> terminarSesion(Long id);

    Optional<SesionDTO> reprogramarSesion(Long id, Horario nuevoHorario);

    Optional<SesionDTO> agregarParticipante(Long id);

    Optional<SesionDTO> removerParticipante(Long id);

    boolean eliminarSesion(Long id);

    long contarPorEstado(EstadoSesion estado);

    long contarPorEntrenador(Long idEntrenador);

    List<SesionDTO> obtenerDisponibles();
}
