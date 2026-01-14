
package com.soft.gymapp.servicios;
import com.soft.gymapp.dominio.sesiones.Horario;
import com.soft.gymapp.dominio.sesiones.Sala;
import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.dominio.sesiones.SesionRepositorio;

import java.util.List;

import com.soft.gymapp.servicios.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SesionServiceImpl implements SesionService {

  private final SesionRepositorio sesionRepositorio;
  private final UsuarioService usuarioService;

  public SesionServiceImpl(SesionRepositorio sesionRepositorio, UsuarioService usuarioService) {
    this.sesionRepositorio = sesionRepositorio;
    this.usuarioService = usuarioService;
  }


  @Override
  @Transactional
  public Sesion programarSesion(Sesion sesion) {
    return sesionRepositorio.save(sesion);
  }

  @Override
  @Transactional
  public void cancelarSesion(int sesionId) {
    Sesion sesion = sesionRepositorio.findById(sesionId).orElseThrow(
        () -> new RuntimeException("Sesión no encontrada"));
    sesion.cancelar();
    sesionRepositorio.save(sesion);
  }

  @Override
  public List<SesionDTO> listarSesionesPorUsuario() {
    UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioLogueado();
    return sesionRepositorio.findByClientes_Id(usuarioDTO.id()).stream().map(Sesion::toDTO).toList();
  }
}
