package com.cancheros.GymApp.Dominio.Rutinas;

import java.util.*;
import java.time.*;

public interface IRutinaRepositorio {
    void guardar(Rutina rutina);

    void actualizar(Rutina rutina);

    void eliminarPorId(String id);

    void buscarPorId(String id);

    void listarTodas();
}