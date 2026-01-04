package com.soft.gymapp.dominio.sesiones;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SesionRepositorio extends JpaRepository<Sesion, Integer> {

    List<Sesion> findByEstado(EstadoSesion estado);

    List<Sesion> findByEntrenadorId(Integer entrenadorId);

    List<Sesion> findBySalaId(Integer salaId);

    List<Sesion> findByClientes_Id(Integer clienteId);
}
