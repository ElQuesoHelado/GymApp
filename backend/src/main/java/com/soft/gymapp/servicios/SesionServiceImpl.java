
package com.soft.gymapp.servicios;
import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.dominio.sesiones.SesionRepositorio;
import com.soft.gymapp.servicios.SesionService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SesionServiceImpl implements SesionService {

  private final SesionRepositorio sesionRepositorio;

  public SesionServiceImpl(SesionRepositorio sesionRepositorio) {
    this.sesionRepositorio = sesionRepositorio;
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
  public List<Sesion> listarSesionesPorUsuario(int usuarioId) {
    return sesionRepositorio.findByClientes_Id(usuarioId);
  }
}
