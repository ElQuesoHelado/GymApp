package com.soft.gymapp.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.soft.gymapp.dominio.notificaciones.Notificacion;
import com.soft.gymapp.dominio.notificaciones.NotificacionRepositorio;
import com.soft.gymapp.dominio.notificaciones.TipoNotificacion;
import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.servicios.dto.NotificacionDTO;
import com.soft.gymapp.servicios.dto.UsuarioDTO;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

  @Mock private NotificacionRepositorio notificacionRepositorio;

  @Mock private UsuarioService usuarioService;

  @InjectMocks private NotificacionServiceImpl notificacionService;

  private Usuario usuario;
  private UsuarioDTO usuarioDTO;

  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setId(1);

    usuarioDTO =
        new UsuarioDTO(1, "Misael", "misael@test.com", "12345678", "CLIENTE");
  }

  // ============================
  // crearNotificacion
  // ============================
  @Test
  void crearNotificacion_datosValidos_seGuardaCorrectamente() {

    when(usuarioService.obtenerUsuarioLogueado()).thenReturn(usuarioDTO);

    Notificacion notificacion = new Notificacion("Mensaje de prueba", usuario,
                                                 TipoNotificacion.MENSAJE);

    when(notificacionRepositorio.save(any(Notificacion.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Notificacion resultado =
        notificacionService.crearNotificacion(notificacion);

    assertNotNull(resultado);
    assertFalse(resultado.isLeido());
    assertNotNull(resultado.getFechaEnvio());

    verify(notificacionRepositorio).save(notificacion);
  }

  @Test
  void crearNotificacion_usuarioNoCoincide_lanzaExcepcion() {

    when(usuarioService.obtenerUsuarioLogueado())
        .thenReturn(
            new UsuarioDTO(2, "Otro", "otro@test.com", "999", "CLIENTE"));

    Notificacion notificacion =
        new Notificacion("Mensaje", usuario, TipoNotificacion.MENSAJE);

    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class,
                     () -> notificacionService.crearNotificacion(notificacion));

    assertEquals("La notificación debe tener un usuario destinatario válido.",
                 ex.getMessage());

    verify(notificacionRepositorio, never()).save(any());
  }

  // ============================
  // obtenerNotificacionPorId
  // ============================
  @Test

  void obtenerNotificacionPorId_existe_retornaDTO() {

    Notificacion notificacion =
        new Notificacion("Mensaje", usuario, TipoNotificacion.MENSAJE);
    notificacion.setId(1);
    notificacion.setFechaEnvio(new Date());

    when(notificacionRepositorio.findById(1))
        .thenReturn(Optional.of(notificacion));

    Optional<NotificacionDTO> resultado =
        notificacionService.obtenerNotificacionPorId(1);

    assertTrue(resultado.isPresent());
    assertEquals("Mensaje", resultado.get().getMensaje()); // 👈 AQUÍ
    verify(notificacionRepositorio).findById(1);
  }

  // ============================
  // marcarComoLeida
  // ============================
  @Test
  void marcarComoLeida_notificacionExiste_seMarcaYGuarda() {

    Notificacion notificacion =
        new Notificacion("Mensaje", usuario, TipoNotificacion.MENSAJE);
    notificacion.setId(1);

    when(notificacionRepositorio.findById(1))
        .thenReturn(Optional.of(notificacion));

    notificacionService.marcarComoLeida(1);

    assertTrue(notificacion.isLeido());
    verify(notificacionRepositorio).save(notificacion);
  }

  // ============================
  // listarNotificacionesPorUsuario
  // ============================
  @Test
  void listarNotificacionesPorUsuario_retornaListaDTOs() {

    when(usuarioService.obtenerUsuarioLogueado()).thenReturn(usuarioDTO);

    when(notificacionRepositorio.findByUsuarioId(1))
        .thenReturn(List.of(
            new Notificacion("Msg 1", usuario, TipoNotificacion.MENSAJE),
            new Notificacion("Msg 2", usuario, TipoNotificacion.MENSAJE)));

    List<NotificacionDTO> resultado =
        notificacionService.listarNotificacionesPorUsuario();

    assertEquals(2, resultado.size());
    verify(notificacionRepositorio).findByUsuarioId(1);
  }

  // ============================
  // listarNotificacionesNoLeidasPorUsuario
  // ============================
  @Test
  void listarNotificacionesNoLeidasPorUsuario_retornaSoloNoLeidas() {

    when(usuarioService.obtenerUsuarioLogueado()).thenReturn(usuarioDTO);

    when(notificacionRepositorio.findByUsuarioIdAndLeidoFalse(1))
        .thenReturn(List.of(
            new Notificacion("Msg", usuario, TipoNotificacion.MENSAJE)));

    List<NotificacionDTO> resultado =
        notificacionService.listarNotificacionesNoLeidasPorUsuario();

    assertEquals(1, resultado.size());
    verify(notificacionRepositorio).findByUsuarioIdAndLeidoFalse(1);
  }
}
