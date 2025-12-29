package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.sesiones.Sesion;
import java.util.List;

public interface SesionService {

  Sesion programarSesion(Sesion sesion);

  void cancelarSesion(int sesionId);

  List<Sesion> listarSesionesPorUsuario(int usuarioId);
}
