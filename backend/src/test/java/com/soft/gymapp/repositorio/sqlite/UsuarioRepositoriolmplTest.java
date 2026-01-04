package com.soft.gymapp.repositorio.sqlite;

import static org.junit.jupiter.api.Assertions.*;

import com.soft.gymapp.dominio.usuarios.Usuario;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioRepositoriolmplTest {

  private UsuarioRepositoriolmpl repositorio;

  @BeforeEach
  void setUp() {
    repositorio = new UsuarioRepositoriolmpl();
  }

  @Test
  void testGuardarYBuscarUsuario() {
    Usuario usuario = new Usuario();
    usuario.setNombre("Juan Perez");
    usuario.setDni("12345678");
    usuario.setEmail("juan@example.com");
    usuario.setTelefono("987654321");
    usuario.setFechaNacimiento(LocalDate.of(1990, 1, 1));

    repositorio.guardar(usuario);

    Optional<Usuario> encontrado =
        repositorio.buscarPorEmail("juan@example.com");
    assertTrue(encontrado.isPresent());
    assertEquals("Juan Perez", encontrado.get().getNombre());
  }

  @Test
  void testActualizarUsuario() {
    Usuario usuario = new Usuario();
    usuario.setNombre("Maria Lopez");
    usuario.setDni("87654321");
    usuario.setEmail("maria@example.com");
    usuario.setTelefono("912345678");
    usuario.setFechaNacimiento(LocalDate.of(1995, 5, 15));

    repositorio.guardar(usuario);

    usuario.setTelefono("900111222");
    repositorio.actualizar(usuario);

    Optional<Usuario> actualizado =
        repositorio.buscarPorEmail("maria@example.com");
    assertTrue(actualizado.isPresent());
    assertEquals("900111222", actualizado.get().getTelefono());
  }

  @Test
  void testEliminarUsuario() {
    Usuario usuario = new Usuario();
    usuario.setNombre("Carlos Diaz");
    usuario.setDni("55555555");
    usuario.setEmail("carlos@example.com");
    usuario.setTelefono("999888777");
    usuario.setFechaNacimiento(LocalDate.of(1988, 8, 8));

    repositorio.guardar(usuario);
    assertEquals(1, repositorio.listarTodos().size());

    repositorio.eliminarPorId(usuario.getId());
    assertEquals(0, repositorio.listarTodos().size());
  }

  @Test
  void testListarUsuarios() {
    Usuario u1 = new Usuario();
    u1.setNombre("Ana Torres");
    u1.setDni("11111111");
    u1.setEmail("ana@example.com");

    Usuario u2 = new Usuario();
    u2.setNombre("Luis Vega");
    u2.setDni("22222222");
    u2.setEmail("luis@example.com");

    repositorio.guardar(u1);
    repositorio.guardar(u2);

    List<Usuario> usuarios = repositorio.listarTodos();
    assertEquals(2, usuarios.size());
  }
}