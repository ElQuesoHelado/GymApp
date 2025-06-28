package com.cancheros.GymApp.Dominio.Usuarios;

import java.util.*;
import java.time.*;

public interface IUsuarioRepositorio {
    void guardar(Usuario usuario);

    void actualizar(Usuario usuario);

    void eliminarPorId(String id);

    void buscarPorId(String id);

    void listarTodos();

}