package com.soft.gymapp.dominio.usuarios;

import com.soft.gymapp.dominio.membresias.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    List<Membresia> findByCuentaUsuarioUsername(String nombre);

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