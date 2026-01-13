package com.soft.gymapp.dominio.planesentrenamiento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para acceder y manipular los planes de entrenamiento.
 */
public interface PlanEntrenamientoRepositorio extends JpaRepository<PlanEntrenamiento, Integer> {
    /**
     * Busca un plan de entrenamiento por el ID del cliente.
     *
     * @param clienteId el ID del cliente
     * @return un Optional con el plan si existe
     */
    Optional<PlanEntrenamiento> findByClienteId(Integer clienteId);

    List<PlanEntrenamiento> findByEntrenadorId(Integer entrenadorId);


}
