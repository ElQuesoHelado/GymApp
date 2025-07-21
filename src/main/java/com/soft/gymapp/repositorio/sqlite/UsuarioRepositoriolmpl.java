package com.soft.gymapp.repositorio.sqlite;

import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UsuarioRepositoriolmpl implements UsuarioRepositorio {

    // --- Simulación de "Base de Datos" en memoria ---
    private final List<Usuario> usuariosDB = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1); // Iniciar en 1

    @Override
    public void guardar(Usuario usuario) {
        if (usuario.getId() == 0) { // Si el ID es 0, es un nuevo usuario a asignar ID
            usuario.setId(idCounter.getAndIncrement()); // Asigna y luego incrementa
        }
        usuariosDB.add(usuario);
        System.out.println("[Repositorio] Usuario guardado: " + usuario.getNombre() + " (ID: " + usuario.getId() + ")");
    }

    @Override
    public void actualizar(Usuario usuario) {
        Optional<Usuario> existingUser = buscarPorId(usuario.getId());
        if (existingUser.isPresent()) {
            int index = usuariosDB.indexOf(existingUser.get());
            if (index != -1) {
                usuariosDB.set(index, usuario); // Reemplaza el objeto en la lista
                System.out.println("[Repositorio] Usuario actualizado: " + usuario.getNombre() + " (ID: " + usuario.getId() + ")");
            }
        } else {
            System.out.println("[Repositorio] Error: Usuario con ID " + usuario.getId() + " no encontrado para actualizar.");
        }
    }

    @Override
    public void eliminarPorId(int id) {
        boolean removed = usuariosDB.removeIf(u -> u.getId() == id);
        if (removed) {
            System.out.println("[Repositorio] Usuario con ID " + id + " eliminado.");
        } else {
            System.out.println("[Repositorio] Error: Usuario con ID " + id + " no encontrado para eliminar.");
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
        return usuariosDB.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    @Override
    public List<Usuario> listarTodos() {
        System.out.println("[Repositorio] Listando todos los usuarios de la 'DB'...");
        return new ArrayList<>(usuariosDB);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuariosDB.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Optional<Usuario> buscarPorDNI(String DNI) {
        return usuariosDB.stream()
                .filter(u -> u.getDNI().equalsIgnoreCase(DNI))
                .findFirst();
    }
}