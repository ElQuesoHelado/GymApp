package com.soft.gymapp.dominio.usuarios;

import com.soft.gymapp.dominio.membresias.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCuentaUsuarioUsername(String nombre);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByDni(String dni);

//    void guardar(Usuario usuario);
//
//    void actualizar(Usuario usuario);
//
//    void eliminarPorId(String id);
//
//    void buscarPorId(String id);
//
//    void listarTodos();

}