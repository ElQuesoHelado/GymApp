package com.cancheros.GymApp.Dominio.Sesiones;

import com.cancheros.GymApp.Dominio.Notificaciones.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.time.*;

public interface ISesionRepositorio extends JpaRepository<Notificacion, Integer> {

    void guardar(Sesion sesion);

    void actualizar(Sesion sesion);

    void eliminarPorId(String id);

    void buscarPorId(String id);

    void listarTodas();

    void listarPorCliente(String clienteId);

    void listarPorEntrenador(String entrenadorId);

}