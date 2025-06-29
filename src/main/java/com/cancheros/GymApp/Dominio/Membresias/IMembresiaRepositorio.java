package com.cancheros.GymApp.Dominio.Membresias;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.time.*;

public interface IMembresiaRepositorio extends JpaRepository<Membresia, Integer> {

    void guardar(Membresia membresia);

    void actualizar(Membresia membresia);

    void eliminarPorId(String id);

    void buscarPorId(String id);

    void listarTodas();

}