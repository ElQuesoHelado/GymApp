package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.membresias.MembresiaRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembresiaServiceTest {

    @Mock
    private MembresiaRepositorio membresiaRepositorio;

    @InjectMocks
    private MembresiaService membresiaService;

    private Membresia membresia;

    @BeforeEach
    void setUp() {
        membresia = new Membresia();
        membresia.setId(1);
        membresia.setEstado(EstadoMembresia.ACTIVADA);
    }

    @Test
    void marcarComoAdeudada_MembresiaExistente_ActualizaEstadoYDeuda() {
        Integer membresiaId = 1;
        double montoDeuda = 150.0;

        when(membresiaRepositorio.findById(membresiaId))
                .thenReturn(Optional.of(membresia));
        when(membresiaRepositorio.save(any(Membresia.class)))
                .thenReturn(membresia);

        Membresia resultado = membresiaService.marcarComoAdeudada(membresiaId, montoDeuda);

        assertNotNull(resultado);
        verify(membresiaRepositorio).findById(membresiaId);
        verify(membresiaRepositorio).save(membresia);
    }

    @Test
    void marcarComoAdeudada_MembresiaNoEncontrada_LanzaExcepcion() {
        Integer membresiaId = 999;
        double montoDeuda = 150.0;

        when(membresiaRepositorio.findById(membresiaId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> membresiaService.marcarComoAdeudada(membresiaId, montoDeuda));

        assertEquals("Membresía no encontrada", exception.getMessage());
        verify(membresiaRepositorio).findById(membresiaId);
        verify(membresiaRepositorio, never()).save(any());
    }

    @Test
    void cancelarMembresia_MembresiaExistente_ActualizaEstadoACancelada() {
        Integer membresiaId = 1;

        when(membresiaRepositorio.findById(membresiaId))
                .thenReturn(Optional.of(membresia));
        when(membresiaRepositorio.save(any(Membresia.class)))
                .thenReturn(membresia);

        Membresia resultado = membresiaService.cancelarMembresia(membresiaId);

        assertNotNull(resultado);
        assertEquals(EstadoMembresia.CANCELADA, membresia.getEstado());
        verify(membresiaRepositorio).findById(membresiaId);
        verify(membresiaRepositorio).save(membresia);
    }

    @Test
    void cancelarMembresia_MembresiaNoEncontrada_LanzaExcepcion() {
        Integer membresiaId = 999;

        when(membresiaRepositorio.findById(membresiaId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> membresiaService.cancelarMembresia(membresiaId));

        assertEquals("Membresía no encontrada", exception.getMessage());
        verify(membresiaRepositorio).findById(membresiaId);
        verify(membresiaRepositorio, never()).save(any());
    }

    @Test
    void buscarPorEstado_EstadoExistente_RetornaListaMembresias() {
        EstadoMembresia estado = EstadoMembresia.ACTIVADA;
        List<Membresia> membresiasEsperadas = Arrays.asList(membresia);

        when(membresiaRepositorio.findByEstado(estado))
                .thenReturn(membresiasEsperadas);

        List<Membresia> resultado = membresiaService.buscarPorEstado(estado);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(membresia, resultado.get(0));
        verify(membresiaRepositorio).findByEstado(estado);
    }

    @Test
    void buscarPorEstado_EstadoSinMembresias_RetornaListaVacia() {
        EstadoMembresia estado = EstadoMembresia.CANCELADA;

        when(membresiaRepositorio.findByEstado(estado))
                .thenReturn(Arrays.asList());

        List<Membresia> resultado = membresiaService.buscarPorEstado(estado);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(membresiaRepositorio).findByEstado(estado);
    }

    @Test
    void buscarPorEstado_EstadoNulo_RetornaListaVacia() {
        when(membresiaRepositorio.findByEstado(null))
                .thenReturn(Arrays.asList());

        List<Membresia> resultado = membresiaService.buscarPorEstado(null);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(membresiaRepositorio).findByEstado(null);
    }
}