package com.soft.gymapp.sesiones.servicio;

import com.soft.gymapp.sesiones.dominio.EstadoSesion;
import com.soft.gymapp.sesiones.dominio.Horario;
import com.soft.gymapp.sesiones.dominio.Sesion;
import com.soft.gymapp.sesiones.dto.CrearSesionDTO;
import com.soft.gymapp.sesiones.dto.SesionDTO;
import com.soft.gymapp.sesiones.repositorio.SesionRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SesionServicioImplTest {

    @Mock
    private SesionRepositorio sesionRepositorio;

    @InjectMocks
    private SesionServicioImpl sesionServicio;

    private Sesion sesionTest;
    private CrearSesionDTO crearDTO;

    @BeforeEach
    void setUp() {
        Horario horario = new Horario(LocalDate.now().plusDays(1), LocalTime.of(10, 0), LocalTime.of(11, 0));
        sesionTest = new Sesion(1L, 1L, horario);
        sesionTest.setId(1L);

        crearDTO = new CrearSesionDTO(1L, 1L, horario);
    }

    @Test
    void testCrearSesion() {
        when(sesionRepositorio.save(any(Sesion.class)))
                .thenReturn(sesionTest);

        SesionDTO resultado = sesionServicio.crearSesion(crearDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(sesionRepositorio, times(1)).save(any(Sesion.class));
    }

    @Test
    void testObtenerPorId() {
        when(sesionRepositorio.findById(1L))
                .thenReturn(Optional.of(sesionTest));

        Optional<SesionDTO> resultado = sesionServicio.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void testConfirmarSesion() {
        when(sesionRepositorio.findById(1L))
                .thenReturn(Optional.of(sesionTest));
        when(sesionRepositorio.save(any(Sesion.class)))
                .thenReturn(sesionTest);

        Optional<SesionDTO> resultado = sesionServicio.confirmarSesion(1L);

        assertTrue(resultado.isPresent());
        assertEquals(EstadoSesion.EN_PROGRESO, resultado.get().getEstado());
    }

    @Test
    void testCancelarSesion() {
        when(sesionRepositorio.findById(1L))
                .thenReturn(Optional.of(sesionTest));
        when(sesionRepositorio.save(any(Sesion.class)))
                .thenReturn(sesionTest);

        Optional<SesionDTO> resultado = sesionServicio.cancelarSesion(1L);

        assertTrue(resultado.isPresent());
        assertEquals(EstadoSesion.CANCELADA, resultado.get().getEstado());
    }

    @Test
    void testAgregarParticipante() {
        when(sesionRepositorio.findById(1L))
                .thenReturn(Optional.of(sesionTest));
        when(sesionRepositorio.save(any(Sesion.class)))
                .thenReturn(sesionTest);

        Optional<SesionDTO> resultado = sesionServicio.agregarParticipante(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getNumeroParticipantes());
    }

    @Test
    void testEliminarSesion() {
        when(sesionRepositorio.existsById(1L))
                .thenReturn(true);

        boolean resultado = sesionServicio.eliminarSesion(1L);

        assertTrue(resultado);
        verify(sesionRepositorio, times(1)).deleteById(1L);
    }

    @Test
    void testContarPorEstado() {
        when(sesionRepositorio.countByEstado(EstadoSesion.SIN_EMPEZAR))
                .thenReturn(5L);

        long resultado = sesionServicio.contarPorEstado(EstadoSesion.SIN_EMPEZAR);

        assertEquals(5L, resultado);
    }
}
