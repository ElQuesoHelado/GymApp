package com.soft.gymapp.dominio.planesentrenamiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanEntrenamientoRepositorio extends JpaRepository<PlanEntrenamiento, Integer> {

    Optional<PlanEntrenamiento> findByClienteId(Integer cliente_id);

//    void guardar(Rutina rutina);
//
//    void actualizar(Rutina rutina);
//
//    void eliminarPorId(String id);
//
//    void buscarPorId(String id);
//
//    void listarTodas();
}