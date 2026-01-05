package com.soft.gymapp.sesiones.repositorio;

import com.soft.gymapp.sesiones.dominio.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Sala.
 */
@Repository
public interface SalaRepositorio extends JpaRepository<Sala, Long> {

    List<Sala> findByActivaTrue();

    List<Sala> findByActivaTrueOrderByNombre();

    boolean existsByNombre(String nombre);
}
