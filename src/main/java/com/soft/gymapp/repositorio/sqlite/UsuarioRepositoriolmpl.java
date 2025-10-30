package com.soft.gymapp.repositorio.sqlite;

import com.soft.gymapp.dominio.usuarios.Usuario;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepositoriolmpl {

  private final List<Usuario> usuariosDB = new ArrayList<>();
  private final AtomicInteger idCounter = new AtomicInteger(1);

  public void guardar(Usuario usuario) {
    if (usuario.getId() == 0) {
      usuario.setId(idCounter.getAndIncrement());
    }
    usuariosDB.add(usuario);
    System.out.println("[Repositorio] Usuario guardado: " +
                       usuario.getNombre());
  }

  public void actualizar(Usuario usuario) {
    buscarPorId(usuario.getId()).ifPresent(u -> {
      int index = usuariosDB.indexOf(u);
      usuariosDB.set(index, usuario);
    });
  }

  public void eliminarPorId(int id) {
    usuariosDB.removeIf(u -> u.getId() == id);
  }

  public Optional<Usuario> buscarPorId(int id) {
    return usuariosDB.stream().filter(u -> u.getId() == id).findFirst();
  }

  public List<Usuario> listarTodos() { return new ArrayList<>(usuariosDB); }

  public Optional<Usuario> buscarPorEmail(String email) {
    return usuariosDB.stream()
        .filter(u -> u.getEmail().equalsIgnoreCase(email))
        .findFirst();
  }

  public Optional<Usuario> buscarPorDNI(String dni) {
    return usuariosDB.stream()
        .filter(u -> u.getDni().equalsIgnoreCase(dni))
        .findFirst();
  }
}
