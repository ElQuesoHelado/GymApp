package com.soft.gymapp.dominio.membresias;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MembresiaRepositorioTest {

    @Autowired
    private MembresiaRepositorio membresiaRepositorio;

    @Autowired
    private TestEntityManager entityManager;

    private Membresia membresiaActiva;
    private Membresia membresiaCancelada;

    @BeforeEach
    void setUp() {
        entityManager.getEntityManager().createQuery("DELETE FROM Membresia").executeUpdate();
        entityManager.flush();

        TipoMembresia tipoPremium = new TipoMembresia();
        tipoPremium.setNombre("Premium");
        tipoPremium.setPrecio(100.0f);
        tipoPremium.setDuracionDias(30);

        TipoMembresia tipoBasico = new TipoMembresia();
        tipoBasico.setNombre("Básico");
        tipoBasico.setPrecio(50.0f);
        tipoBasico.setDuracionDias(15);

        membresiaActiva = new Membresia();
        membresiaActiva.setId(999);
        membresiaActiva.setEstado(EstadoMembresia.ACTIVADA);
        membresiaActiva.setTipo(tipoPremium);
        entityManager.persist(membresiaActiva);

        membresiaCancelada = new Membresia();
        membresiaCancelada.setId(1000);
        membresiaCancelada.setEstado(EstadoMembresia.CANCELADA);
        membresiaCancelada.setTipo(tipoBasico);
        entityManager.persist(membresiaCancelada);

        entityManager.flush();
    }

    @Test
    void findByEstado_EstadoActiva_RetornaMembresiasActivas() {
        List<Membresia> resultado = membresiaRepositorio.findByEstado(EstadoMembresia.ACTIVADA);

        assertEquals(1, resultado.size());
        assertEquals(EstadoMembresia.ACTIVADA, resultado.get(0).getEstado());
    }

    @Test
    void findByEstado_EstadoCancelada_RetornaMembresiasCanceladas() {
        List<Membresia> resultado = membresiaRepositorio.findByEstado(EstadoMembresia.CANCELADA);

        assertEquals(1, resultado.size());
        assertEquals(EstadoMembresia.CANCELADA, resultado.get(0).getEstado());
    }

    @Test
    void findByTipoNombreContainingIgnoreCase_NombreExistente_RetornaMembresias() {
        List<Membresia> resultado = membresiaRepositorio.findByTipoNombreContainingIgnoreCase("premium");

        assertEquals(1, resultado.size());
        assertEquals("Premium", resultado.get(0).getTipo().getNombre());
    }

    @Test
    void findByTipoPrecioLessThan_PrecioValido_RetornaMembresiasMasBaratas() {
        List<Membresia> resultado = membresiaRepositorio.findByTipoPrecioLessThan(75.0f);

        assertEquals(1, resultado.size());
        assertEquals("Básico", resultado.get(0).getTipo().getNombre());
    }

    @Test
    void findByTipoDuracionDiasGreaterThanEqual_DuracionValida_RetornaMembresiasConDuracionSuficiente() {
        List<Membresia> resultado = membresiaRepositorio.findByTipoDuracionDiasGreaterThanEqual(20);

        assertEquals(1, resultado.size());
        assertEquals("Premium", resultado.get(0).getTipo().getNombre());
    }
}