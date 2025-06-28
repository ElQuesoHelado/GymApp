package com.cancheros.GymApp.Dominio.Notificaciones;

import java.util.*;
import java.time.*;

public interface INotificacionRepositorio {
    void guardar(Notificacion n);

    void actualizar(Notificacion n);

    void listarTodas();

    void buscarPorId(String id);

    void eliminarPorId(String id);
}