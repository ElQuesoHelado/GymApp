package com.soft.gymapp.dominio.notificaciones;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import com.soft.gymapp.dominio.usuarios.Usuario;

@DataJpaTest
class NotificacionRepositorioTest {

    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    @Test
    void testGuardarYBuscarNotificacion() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        Notificacion n = new Notificacion("Hola", usuario, TipoNotificacion.MENSAJE);
        n.setLeido(false);

        Notificacion guardada = notificacionRepositorio.save(n);
        assertNotNull(guardada.getId());
        Notificacion encontrada = notificacionRepositorio.findById(guardada.getId()).orElse(null);
        assertNotNull(encontrada);
        assertEquals("Hola", encontrada.getMensaje());
        assertEquals(usuario.getId(), encontrada.getUsuario().getId());
    }
}