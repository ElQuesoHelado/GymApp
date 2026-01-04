package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.notificaciones.*;
import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.dominio.usuarios.UsuarioRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionServiceTest {

    @Mock
    private NotificacionRepositorio notificacionRepositorio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearNotificacion() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        when(usuarioRepositorio.findById(1)).thenReturn(Optional.of(usuario));
        Notificacion n = new Notificacion("Mensaje de prueba", usuario, TipoNotificacion.MENSAJE);
        when(notificacionRepositorio.save(any(Notificacion.class))).thenReturn(n);

        Notificacion resultado = notificacionService.crearNotificacion(n);
        assertNotNull(resultado);
        verify(notificacionRepositorio).save(n);
    }

    @Test
    void testObtenerNotificacionPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        Notificacion n = new Notificacion("Mensaje de prueba", usuario, TipoNotificacion.MENSAJE);
        when(notificacionRepositorio.findById(1)).thenReturn(Optional.of(n));

        Optional<Notificacion> resultado = notificacionService.obtenerNotificacionPorId(1);
        assertTrue(resultado.isPresent());
        verify(notificacionRepositorio).findById(1);
    }
}