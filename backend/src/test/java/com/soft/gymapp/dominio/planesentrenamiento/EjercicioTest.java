package com.soft.gymapp.dominio.planesentrenamiento;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EjercicioTest {

  private Ejercicio ejercicio;

  @BeforeEach
  void setUp() {
    ejercicio = new Ejercicio();
  }

  @Test
  void calcularCaloriasQuemadas() {
    int duracion = 30;

    float resultado = ejercicio.calcularCaloriasQuemadas(duracion);

    assertEquals(duracion, resultado);
  }

  @Test
  void setIdValorPositivoAsignaCorrectamente() {
    Integer id = 1;

    ejercicio.setId(id);

    assertEquals(id, ejercicio.getId());
  }

  @Test
  void setIdValorNuloNoAsignaValor() {
    ejercicio.setId(1);

    ejercicio.setId(null);

    assertEquals(1, ejercicio.getId());
  }

  @Test
  void setIdValorCeroNoAsignaValor() {
    ejercicio.setId(1);

    ejercicio.setId(0);

    assertEquals(1, ejercicio.getId());
  }

  @Test
  void setIdValorNegativoNoAsignaValor() {
    ejercicio.setId(1);

    ejercicio.setId(-1);

    assertEquals(1, ejercicio.getId());
  }

  @Test
  void setNombreValorValidoAsignaCorrectamente() {
    String nombre = "  Sentadillas  ";
    String expected = "Sentadillas";

    ejercicio.setNombre(nombre);

    assertEquals(expected, ejercicio.getNombre());
  }

  @Test
  void setNombreValorNuloNoAsignaValor() {
    ejercicio.setNombre("Inicial");

    ejercicio.setNombre(null);

    assertEquals("Inicial", ejercicio.getNombre());
  }

  @Test
  void setNombreStringVacioNoAsignaValor() {
    ejercicio.setNombre("Inicial");

    ejercicio.setNombre("");

    assertEquals("Inicial", ejercicio.getNombre());
  }

  @Test
  void setDescripcionValorValidoAsignaCorrectamente() {
    String descripcion = "  Ejercicio para piernas  ";
    String expected = "Ejercicio para piernas";

    ejercicio.setDescripcion(descripcion);

    assertEquals(expected, ejercicio.getDescripcion());
  }

  @Test
  void setDescripcionValorNuloNoAsignaValor() {
    ejercicio.setDescripcion("Inicial");

    ejercicio.setDescripcion(null);

    assertEquals("Inicial", ejercicio.getDescripcion());
  }

  @Test
  void setDescripcionStringVacioNoAsignaValor() {
    ejercicio.setDescripcion("Inicial");

    ejercicio.setDescripcion("");

    assertEquals("Inicial", ejercicio.getDescripcion());
  }

  @Test
  void setRepeticionesValorPositivoAsignaCorrectamente() {
    int repeticiones = 15;

    ejercicio.setRepeticiones(repeticiones);

    assertEquals(repeticiones, ejercicio.getRepeticiones());
  }

  @Test
  void setRepeticionesValorNegativoNoAsignaValor() {
    ejercicio.setRepeticiones(10);

    ejercicio.setRepeticiones(-5);

    assertEquals(10, ejercicio.getRepeticiones());
  }

  @Test
  void setSeriesValorPositivoAsignaCorrectamente() {
    int series = 4;

    ejercicio.setSeries(series);

    assertEquals(series, ejercicio.getSeries());
  }

  @Test
  void setSeriesValorNegativoNoAsignaValor() {
    ejercicio.setSeries(3);

    ejercicio.setSeries(-2);

    assertEquals(3, ejercicio.getSeries());
  }

  @Test
  void setRutinaValorValidoAsignaCorrectamente() {
    Rutina rutina = new Rutina();

    ejercicio.setRutina(rutina);

    assertEquals(rutina, ejercicio.getRutina());
  }

  @Test
  void setRutinaValorNuloAsignaCorrectamente() {
    ejercicio.setRutina(null);

    assertNull(ejercicio.getRutina());
  }
}