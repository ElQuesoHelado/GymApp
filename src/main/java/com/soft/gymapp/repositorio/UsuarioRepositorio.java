package com.soft.gymapp.repositorio;

import com.soft.gymapp.dominio.usuarios.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio {
  void guardar(Usuario usuario);
  void actualizar(Usuario usuario);
  void eliminarPorId(int id);
  Optional<Usuario> buscarPorId(int id);
  List<Usuario> listarTodos();
  Optional<Usuario> buscarPorEmail(String email);
  Optional<Usuario> buscarPorDNI(String dni);
}
