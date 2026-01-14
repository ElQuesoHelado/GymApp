package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.servicios.dto.SesionDTO;

import java.util.List;

public interface SesionService {

  Sesion programarSesion(Sesion sesion);

  void cancelarSesion(int sesionId);

  List<SesionDTO> listarSesionesPorUsuario();
}
