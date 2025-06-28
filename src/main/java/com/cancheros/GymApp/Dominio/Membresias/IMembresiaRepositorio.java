package com.cancheros.GymApp.Dominio.Membresias;

import java.util.*;
import java.time.*;

public interface IMembresiaRepositorio {

    void guardar(Membresia membresia);

    void actualizar(Membresia membresia);

    void eliminarPorId(String id);

    void buscarPorId(String id);

    void listarTodas();

}