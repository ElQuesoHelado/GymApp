package com.cancheros.GymApp.Dominio.Usuarios;

import com.cancheros.GymApp.Dominio.Membresias.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.time.*;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    void guardar(Usuario usuario);

    void actualizar(Usuario usuario);

    void eliminarPorId(String id);

    void buscarPorId(String id);

    void listarTodos();

}