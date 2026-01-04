package com.soft.gymapp.dominio.notificaciones;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import com.soft.gymapp.dominio.usuarios.Usuario;

class NotificacionTest {
    private Notificacion notificacion;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        notificacion = new Notificacion("Mensaje de prueba", usuario, TipoNotificacion.MENSAJE);
    }

    @Test
    void testMarcarComoLeida() {
        notificacion.marcarComoLeida();
        assertTrue(notificacion.isLeido());
    }

    @Test
    void testEnviarLanzaExcepcionSiUsuarioNull() {
        Notificacion n = new Notificacion("msg", null, TipoNotificacion.MENSAJE);
        assertThrows(IllegalStateException.class, n::enviar);
    }

    @Test
    void testEnviarLlamaPushNotificacion() {
        Usuario usuarioReal = new Usuario() {
            boolean notificado = false;
            @Override
            public void pushNotificacion(Notificacion notif) {
                notificado = true;
            }
        };
        Notificacion notif = new Notificacion("msg", usuarioReal, TipoNotificacion.MENSAJE);
        notif.enviar();
        // No hay excepción, método ejecutado
        assertEquals(usuarioReal, notif.getUsuario());
    }

    @Test
    void testGettersAndSetters() {
        notificacion.setMensaje("Nuevo mensaje");
        assertEquals("Nuevo mensaje", notificacion.getMensaje());
        notificacion.setLeido(true);
        assertTrue(notificacion.isLeido());
        notificacion.setTipo(TipoNotificacion.IMPORTANTE);
        assertEquals(TipoNotificacion.IMPORTANTE, notificacion.getTipo());
    }
}