package com.soft.gymapp.dominio.usuarios;

import static org.junit.jupiter.api.Assertions.*;

import com.soft.gymapp.dominio.notificaciones.Notificacion;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioTest {

  private Usuario usuario;

  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setId(1);
    usuario.setNombre("Misael");
    usuario.setDni("12345678");
    usuario.setEmail("misael@test.com");
    usuario.setTelefono("999999999");
    usuario.setFechaNacimiento(LocalDate.of(2000, 1, 1));
  }

  // ---------- GETTERS / SETTERS ----------

  @Test
  void deberiaSetearYObtenerDatosBasicos() {
    assertEquals(1, usuario.getId());
    assertEquals("Misael", usuario.getNombre());
    assertEquals("12345678", usuario.getDni());
    assertEquals("misael@test.com", usuario.getEmail());
    assertEquals("999999999", usuario.getTelefono());
    assertEquals(LocalDate.of(2000, 1, 1), usuario.getFechaNacimiento());
  }

  // ---------- CUENTA USUARIO ----------

  @Test
  void deberiaAsignarCuentaUsuario() {
    CuentaUsuario cuenta =
        new CuentaUsuario("misael", "password123", EstadoCuentaUsuario.ACTIVA);

    usuario.setCuentaUsuario(cuenta);

    assertNotNull(usuario.getCuentaUsuario());
    assertEquals("misael", usuario.getCuentaUsuario().getUsername());
    assertEquals(EstadoCuentaUsuario.ACTIVA,
                 usuario.getCuentaUsuario().getEstado());
  }

  // ---------- NOTIFICACIONES ----------

  @Test
  void pushNotificacionDeberiaAgregarNotificacion() {
    Notificacion notificacion = new Notificacion();
    usuario.pushNotificacion(notificacion);

    List<Notificacion> notificaciones = usuario.getNotificaciones();

    assertEquals(1, notificaciones.size());
    assertTrue(notificaciones.contains(notificacion));
  }

  @Test
  void setNotificacionesDeberiaReemplazarLista() {
    Notificacion n1 = new Notificacion();
    Notificacion n2 = new Notificacion();

    usuario.setNotificaciones(List.of(n1, n2));

    assertEquals(2, usuario.getNotificaciones().size());
  }

  // ---------- SESIÓN (no hacen nada, pero deben ejecutarse) ----------

  @Test
  void iniciarSesionNoDebeLanzarExcepcion() {
    assertDoesNotThrow(() -> usuario.iniciarSesion());
  }

  @Test
  void cerrarSesionnoDebeLanzarExcepcion() {
    assertDoesNotThrow(() -> usuario.cerrarSesion());
  }
}
