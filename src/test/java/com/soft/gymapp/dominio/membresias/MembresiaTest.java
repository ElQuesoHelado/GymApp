// src/test/java/com/soft/gymapp/unit/dominio/membresias/MembresiaTest.java
package com.soft.gymapp.dominio.membresias;

import static org.junit.jupiter.api.Assertions.*;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.membresias.TipoMembresia;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MembresiaTest {

  private Membresia membresia;
  private TipoMembresia tipo;

  @BeforeEach
  void setUp() {
    membresia = new Membresia();
    tipo = new TipoMembresia();
  }

  @Test
  void testCrearMembresiaConDatosValidos() {
    // Arrange
    Date fechaInicio = new Date();
    Date fechaFin = new Date(System.currentTimeMillis() + 86400000); // +1 día

    tipo.setNombre("Mensual");
    tipo.setDuracionDias(30);
    tipo.setPrecio(100.0f);

    // Act
    membresia.setId(1);
    membresia.setFechaInicio(fechaInicio);
    membresia.setFechaFin(fechaFin);
    membresia.setEstado(EstadoMembresia.ACTIVADA);
    membresia.setTipo(tipo);

    // Assert
    assertEquals(1, membresia.getId());
    assertEquals(fechaInicio, membresia.getFechaInicio());
    assertEquals(fechaFin, membresia.getFechaFin());
    assertEquals(EstadoMembresia.ACTIVADA, membresia.getEstado());
    assertEquals("Mensual", membresia.getTipo().getNombre());
    assertEquals(30, membresia.getTipo().getDuracionDias());
    assertEquals(100.0f, membresia.getTipo().getPrecio(), 0.001f);
  }

  @Test
  void testValidacionesTipoMembresiaNombreNulo() {
    Exception exception = assertThrows(IllegalArgumentException.class,
                                       () -> tipo.setNombre(null));
    assertEquals("El nombre no puede ser nulo o vacío", exception.getMessage());
  }

  @Test
  void testValidacionesTipoMembresiaNombreVacio() {
    Exception exception = assertThrows(IllegalArgumentException.class,
                                       () -> tipo.setNombre("   "));
    assertEquals("El nombre no puede ser nulo o vacío", exception.getMessage());
  }

  @Test
  void testValidacionesTipoMembresiaDuracionCero() {
    Exception exception = assertThrows(IllegalArgumentException.class,
                                       () -> tipo.setDuracionDias(0));
    assertEquals("La duración debe ser positiva", exception.getMessage());
  }

  @Test
  void testValidacionesTipoMembresiaPrecioNegativo() {
    Exception exception = assertThrows(IllegalArgumentException.class,
                                       () -> tipo.setPrecio(-50.0f));
    assertEquals("El precio no puede ser negativo", exception.getMessage());
  }

  @Test
  void testCalcularPrecioPorDia() {
    // Arrange
    tipo.setPrecio(300.0f);
    tipo.setDuracionDias(30);

    // Act
    float precioPorDia = tipo.calcularPrecioPorDia();

    // Assert
    assertEquals(10.0f, precioPorDia, 0.001f);
  }

  @Test
  void testCalcularPrecioPorDiaConDuracionCero() {
    // Arrange + Assert
    assertThrows(IllegalArgumentException.class, () -> {
      tipo.setPrecio(100.0f);
      tipo.setDuracionDias(0); // debe lanzar excepción
    }, "Se esperaba una excepción por duración igual a cero");
  }
}
