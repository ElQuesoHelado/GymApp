package com.cancheros.GymApp.Dominio.Sesiones;

import java.util.*;
import java.time.*;

public interface ISesionRepositorio {

    void guardar(Sesion sesion);

    void actualizar(Sesion sesion);

    void eliminarPorId(String id);

    void buscarPorId(String id);

    void listarTodas();

    void listarPorCliente(String clienteId);

    void listarPorEntrenador(String entrenadorId);

}