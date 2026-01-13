package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.membresias.MembresiaRepositorio;
import com.soft.gymapp.dominio.membresias.TipoMembresia;
import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.servicios.dto.MembresiaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
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
    private MembresiaServiceImpl membresiaService;

    private Membresia membresia;

    @BeforeEach
    void setUp() {

        Cliente cliente = new Cliente();
        cliente.setId(1);

        TipoMembresia tipo = new TipoMembresia();
        tipo.setNombre("Premium");
        tipo.setDuracionDias(30);
        tipo.setPrecio(120.0f); // <-- float

        membresia = new Membresia();
        membresia.setId(1);
        membresia.setFechaInicio(new Date());
        membresia.setFechaFin(new Date());
        membresia.setEstado(EstadoMembresia.ACTIVADA);
        membresia.setDeuda(0.0);
        membresia.setTipo(tipo);
        membresia.setCliente(cliente);
        membresia.setPagosMembresia(Collections.emptyList());
    }

    @Test
    void marcarComoAdeudadamembresiaExisteactualizaEstadoYDeuda() {

        when(membresiaRepositorio.findById(1))
                .thenReturn(Optional.of(membresia));
        when(membresiaRepositorio.save(any()))
                .thenReturn(membresia);

        Membresia resultado =
                membresiaService.marcarComoAdeudada(1, 150.0);

        assertEquals(EstadoMembresia.ADEUDADA, resultado.getEstado());
        assertEquals(150.0, resultado.getDeuda());
    }

    @Test
    void marcarComoAdeudadamembresiaNoExistelanzaExcepcion() {

        when(membresiaRepositorio.findById(99))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> membresiaService.marcarComoAdeudada(99, 100));

        assertEquals("Membresía no encontrada", ex.getMessage());
    }

    @Test
    void cancelarMembresiamembresiaExistecambiaEstado() {

        when(membresiaRepositorio.findById(1))
                .thenReturn(Optional.of(membresia));
        when(membresiaRepositorio.save(any()))
                .thenReturn(membresia);

        Membresia resultado =
                membresiaService.cancelarMembresia(1);

        assertEquals(EstadoMembresia.CANCELADA, resultado.getEstado());
    }

    @Test
    void buscarPorEstadoconResultadosretornaDTOs() {

        when(membresiaRepositorio.findByEstado(EstadoMembresia.ACTIVADA))
                .thenReturn(List.of(membresia));

        List<MembresiaDTO> resultado =
                membresiaService.buscarPorEstado(EstadoMembresia.ACTIVADA);

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).clienteId());
        assertEquals("Premium", resultado.get(0).tipo().nombre());
    }
}
