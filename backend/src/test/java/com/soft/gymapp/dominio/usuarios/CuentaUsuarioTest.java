package com.soft.gymapp.dominio.usuarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CuentaUsuarioTest {

  private CuentaUsuario cuenta;

  @BeforeEach
  void setUp() {
    cuenta =
        new CuentaUsuario("misael", "password123", EstadoCuentaUsuario.ACTIVA);
  }

  // ======================
  // cambiarPassword
  // ======================

  @Test
  void cambiarPassworddeberiaCambiarCorrectamente() {
    cuenta.cambiarPassword("nuevaPass123", "password123");

    assertEquals("nuevaPass123", cuenta.getPassword());
  }

  @Test
  void cambiarPasswordconPasswordActualIncorrectoDebeLanzarSecurityException() {
    SecurityException ex = assertThrows(
        SecurityException.class,
        () -> cuenta.cambiarPassword("nuevaPass123", "incorrecto"));

    assertEquals("Credenciales invalidas", ex.getMessage());
  }

  @Test
  void
  cambiarPasswordconPasswordNuevoMuyCortoDebeLanzarIllegalArgumentException() {
    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class,
                     () -> cuenta.cambiarPassword("123", "password123"));

    assertEquals("Contrasenia muy corta", ex.getMessage());
  }

  @Test
  void
  cambiarPasswordconPasswordIgualAlAnteriorDebeLanzarIllegalArgumentException() {
    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> cuenta.cambiarPassword("password123", "password123"));

    assertEquals("Contrasenia identica a anterior", ex.getMessage());
  }

  // ======================
  // bloquearCuenta
  // ======================

  @Test
  void bloquearCuentaDebeCambiarEstadoABloqueada() {
    cuenta.bloquearCuenta();

    assertEquals(EstadoCuentaUsuario.BLOQUEADA, cuenta.getEstado());
  }

  // ======================
  // getters y setters
  // ======================

  @Test
  void settersYGettersdeberianFuncionar() {
    cuenta.setUsername("nuevoUsuario");
    cuenta.setEstado(EstadoCuentaUsuario.BLOQUEADA);

    assertEquals("nuevoUsuario", cuenta.getUsername());
    assertEquals(EstadoCuentaUsuario.BLOQUEADA, cuenta.getEstado());
  }
}
