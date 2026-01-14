package com.soft.gymapp.dominio.membresias;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PagoMembresiaTest {

  private PagoMembresia pago;

  @BeforeEach
  void setUp() {
    pago = new PagoMembresia();
  }

  @Test
  void settersYGettersdeberianFuncionarCorrectamente() {
    Date fecha = new Date();
    Membresia membresia = mock(Membresia.class);

    pago.setId("PAGO-001");
    pago.setMonto(150.75f);
    pago.setFechaPago(fecha);
    pago.setMembresia(membresia);
    pago.setMetodoPago(MetodoPago.TARJETA);

    assertEquals("PAGO-001", pago.getId());
    assertEquals(150.75f, pago.getMonto());
    assertEquals(fecha, pago.getFechaPago());
    assertEquals(membresia, pago.getMembresia());
    assertEquals(MetodoPago.TARJETA, pago.getMetodoPago());
  }

  @Test
  void setMetodoPagodeberiaAceptarTodosLosValoresEnum() {
    pago.setMetodoPago(MetodoPago.EFECTIVO);
    assertEquals(MetodoPago.EFECTIVO, pago.getMetodoPago());

    pago.setMetodoPago(MetodoPago.TARJETA);
    assertEquals(MetodoPago.TARJETA, pago.getMetodoPago());
  }

  @Test
  void setMembresiaaliasSetdeberiaAsignarCorrectamente() {
    Membresia membresia = mock(Membresia.class);

    pago.set(membresia);

    assertEquals(membresia, pago.getMembresia());
  }

  @Test
  void procesarPagonoDeberiaLanzarExcepcion() {
    assertDoesNotThrow(() -> pago.procesarPago());
  }

  @Test
  void verificarPagonoDeberiaLanzarExcepcion() {
    assertDoesNotThrow(() -> pago.verificarPago());
  }
}
