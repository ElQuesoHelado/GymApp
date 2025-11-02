package com.soft.gymapp.dominio.planesentrenamiento;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

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
    void setId_ValorPositivo_AsignaCorrectamente() {
        Integer id = 1;

        ejercicio.setId(id);

        assertEquals(id, ejercicio.getId());
    }

    @Test
    void setId_ValorNulo_NoAsignaValor() {
        ejercicio.setId(1);

        ejercicio.setId(null);

        assertEquals(1, ejercicio.getId());
    }

    @Test
    void setId_ValorCero_NoAsignaValor() {
        ejercicio.setId(1);

        ejercicio.setId(0);

        assertEquals(1, ejercicio.getId());
    }

    @Test
    void setId_ValorNegativo_NoAsignaValor() {
        ejercicio.setId(1);

        ejercicio.setId(-1);

        assertEquals(1, ejercicio.getId());
    }

    @Test
    void setNombre_ValorValido_AsignaCorrectamente() {
        String nombre = "  Sentadillas  ";
        String expected = "Sentadillas";

        ejercicio.setNombre(nombre);

        assertEquals(expected, ejercicio.getNombre());
    }

    @Test
    void setNombre_ValorNulo_NoAsignaValor() {
        ejercicio.setNombre("Inicial");

        ejercicio.setNombre(null);

        assertEquals("Inicial", ejercicio.getNombre());
    }

    @Test
    void setNombre_StringVacio_NoAsignaValor() {
        ejercicio.setNombre("Inicial");

        ejercicio.setNombre("");

        assertEquals("Inicial", ejercicio.getNombre());
    }

    @Test
    void setDescripcion_ValorValido_AsignaCorrectamente() {
        String descripcion = "  Ejercicio para piernas  ";
        String expected = "Ejercicio para piernas";

        ejercicio.setDescripcion(descripcion);

        assertEquals(expected, ejercicio.getDescripcion());
    }

    @Test
    void setDescripcion_ValorNulo_NoAsignaValor() {
        ejercicio.setDescripcion("Inicial");

        ejercicio.setDescripcion(null);

        assertEquals("Inicial", ejercicio.getDescripcion());
    }

    @Test
    void setDescripcion_StringVacio_NoAsignaValor() {
        ejercicio.setDescripcion("Inicial");

        ejercicio.setDescripcion("");

        assertEquals("Inicial", ejercicio.getDescripcion());
    }

    @Test
    void setRepeticiones_ValorPositivo_AsignaCorrectamente() {
        int repeticiones = 15;

        ejercicio.setRepeticiones(repeticiones);

        assertEquals(repeticiones, ejercicio.getRepeticiones());
    }

    @Test
    void setRepeticiones_ValorNegativo_NoAsignaValor() {
        ejercicio.setRepeticiones(10);

        ejercicio.setRepeticiones(-5);

        assertEquals(10, ejercicio.getRepeticiones());
    }

    @Test
    void setSeries_ValorPositivo_AsignaCorrectamente() {
        int series = 4;

        ejercicio.setSeries(series);

        assertEquals(series, ejercicio.getSeries());
    }

    @Test
    void setSeries_ValorNegativo_NoAsignaValor() {
        ejercicio.setSeries(3);

        ejercicio.setSeries(-2);

        assertEquals(3, ejercicio.getSeries());
    }

    @Test
    void setRutina_ValorValido_AsignaCorrectamente() {
        Rutina rutina = new Rutina();

        ejercicio.setRutina(rutina);

        assertEquals(rutina, ejercicio.getRutina());
    }

    @Test
    void setRutina_ValorNulo_AsignaCorrectamente() {
        ejercicio.setRutina(null);

        assertNull(ejercicio.getRutina());
    }
}