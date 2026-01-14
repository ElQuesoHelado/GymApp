package com.soft.gymapp.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.dominio.sesiones.SesionRepositorio;
import com.soft.gymapp.servicios.dto.SesionDTO;
import com.soft.gymapp.servicios.dto.UsuarioDTO;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SesionServiceImplTest {

  @Mock private SesionRepositorio sesionRepositorio;

  @Mock private UsuarioService usuarioService;

  @InjectMocks private SesionServiceImpl sesionService;

  private Sesion sesion;

  @BeforeEach
  void setUp() {
    sesion = mock(Sesion.class);
  }

  // =========================
  // programarSesion
  // =========================
  @Test
  void programarSesionDeberiaGuardarSesion() {

    when(sesionRepositorio.save(sesion)).thenReturn(sesion);

    Sesion resultado = sesionService.programarSesion(sesion);

    assertNotNull(resultado);
    verify(sesionRepositorio, times(1)).save(sesion);
  }

  // =========================
  // cancelarSesion
  // =========================
  @Test
  void cancelarSesionDeberiaCancelarSesionExistente() {

    when(sesionRepositorio.findById(1)).thenReturn(Optional.of(sesion));

    sesionService.cancelarSesion(1);

    verify(sesionRepositorio).findById(1);
    verify(sesion).cancelar();
    verify(sesionRepositorio).save(sesion);
  }

  @Test
  void cancelarSesionDeberiaLanzarExcepcionSiNoExiste() {

    when(sesionRepositorio.findById(99)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(
        RuntimeException.class, () -> sesionService.cancelarSesion(99));

    assertEquals("Sesión no encontrada", exception.getMessage());
    verify(sesionRepositorio, never()).save(any());
  }

  // =========================
  // listarSesionesPorUsuario
  // =========================
  @Test
  void listarSesionesPorUsuarioDeberiaRetornarListaDeSesionDTO() {

    UsuarioDTO usuarioDTO =
        new UsuarioDTO(1, "Misael", "misael@test.com", "12345678", "CLIENTE");

    SesionDTO sesionDTO = mock(SesionDTO.class);

    when(usuarioService.obtenerUsuarioLogueado()).thenReturn(usuarioDTO);

    when(sesionRepositorio.findByClientes_Id(1)).thenReturn(List.of(sesion));

    when(sesion.toDTO()).thenReturn(sesionDTO);

    List<SesionDTO> resultado = sesionService.listarSesionesPorUsuario();

    assertEquals(1, resultado.size());
    assertEquals(sesionDTO, resultado.get(0));

    verify(usuarioService).obtenerUsuarioLogueado();
    verify(sesionRepositorio).findByClientes_Id(1);
    verify(sesion).toDTO();
  }
}
