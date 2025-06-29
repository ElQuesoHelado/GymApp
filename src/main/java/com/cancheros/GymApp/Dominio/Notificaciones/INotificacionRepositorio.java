package com.cancheros.GymApp.Dominio.Notificaciones;

import com.cancheros.GymApp.Dominio.Membresias.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.time.*;

public interface INotificacionRepositorio extends JpaRepository<Notificacion, Integer> {
    void guardar(Notificacion n);

    void actualizar(Notificacion n);

    void listarTodas();

    void buscarPorId(String id);

    void eliminarPorId(String id);
}