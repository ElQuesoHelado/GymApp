package com.soft.gymapp.dominio.planesentrenamiento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.soft.gymapp.dominio.membresias.Membresia;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RutinaTest {

  private Rutina rutina;

  @BeforeEach
  void setUp() {
    rutina = new Rutina("Rutina Fuerza", "Ganar masa muscular");
  }

  // ------------------------------
  // agregarEjercicio
  // ------------------------------

  @Test
  void agregarEjerciciovalidodeberiaAgregarYAsignarRutina() {
    Ejercicio ejercicio = mock(Ejercicio.class);

    rutina.agregarEjercicio(ejercicio);

    assertEquals(1, rutina.getEjercicios().size());
    assertTrue(rutina.getEjercicios().contains(ejercicio));
    verify(ejercicio).setRutina(rutina);
  }

  @Test
  void agregarEjercicionulonoDeberiaAgregarNada() {
    rutina.agregarEjercicio(null);

    assertTrue(rutina.getEjercicios().isEmpty());
  }

  // ------------------------------
  // quitarEjercicio
  // ------------------------------

  @Test
  void quitarEjercicioexistentedeberiaEliminarYQuitarRelacion() {
    Ejercicio ejercicio = mock(Ejercicio.class);
    rutina.agregarEjercicio(ejercicio);

    rutina.quitarEjercicio(ejercicio);

    assertTrue(rutina.getEjercicios().isEmpty());
    verify(ejercicio).setRutina(null);
  }

  @Test
  void quitarEjercicionulonoDeberiaFallar() {
    assertDoesNotThrow(() -> rutina.quitarEjercicio(null));
  }

  // ------------------------------
  // asignarRutina a Membresia
  // ------------------------------

  @Test
  void asignarRutinamembresiaValidadeberiaRetornarTrue() {
    Membresia membresia = mock(Membresia.class);
    when(membresia.isVencida()).thenReturn(false);

    boolean resultado = rutina.asignarRutina(membresia);

    assertTrue(resultado);
  }

  @Test
  void asignarRutinamembresiaVencidadeberiaRetornarFalse() {
    Membresia membresia = mock(Membresia.class);
    when(membresia.isVencida()).thenReturn(true);

    boolean resultado = rutina.asignarRutina(membresia);

    assertFalse(resultado);
  }

  @Test
  void asignarRutinamembresiaNuladeberiaRetornarFalse() {
    boolean resultado = rutina.asignarRutina(null);

    assertFalse(resultado);
  }

  // ------------------------------
  // getters y setters
  // ------------------------------

  @Test
  void gettersdeberianRetornarValoresCorrectos() {
    assertEquals("Rutina Fuerza", rutina.getNombre());
    assertEquals("Ganar masa muscular", rutina.getObjetivo());
    assertNotNull(rutina.getEjercicios());
  }

  @Test
  void setEjerciciosdeberiaReemplazarLista() {
    Ejercicio e1 = mock(Ejercicio.class);
    rutina.setEjercicios(List.of(e1));

    assertEquals(1, rutina.getEjercicios().size());
  }
}
