package com.soft.gymapp.repositorio.sqlite;

import com.soft.gymapp.dominio.usuarios.Usuario;
import java.util.*;
import java.util.Optional;

public class UsuarioRepositoriolmpl {
    private final List<Usuario> usuarios = new ArrayList<>();

    public void guardar(Usuario usuario) {
        usuario.setId(usuarios.size() + 1); // Simulación de ID
        usuarios.add(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarios.stream().filter(u -> email.equals(u.getEmail())).findFirst();
    }

    public void actualizar(Usuario usuario) {
        // Simulación: nada que hacer, ya está en la lista
    }

    public void eliminarPorId(Integer id) {
        usuarios.removeIf(u -> Objects.equals(u.getId(), id));
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
}
