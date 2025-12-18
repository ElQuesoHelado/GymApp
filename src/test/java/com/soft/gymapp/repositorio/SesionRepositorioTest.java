package com.soft.gymapp.dominio.sesiones;

import com.soft.gymapp.dominio.usuarios.Entrenador;
import com.soft.gymapp.dominio.usuarios.EntrenadorRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) 
class SesionRepositorioTest {


    @Autowired
    private SesionRepositorio sesionRepositorio;

    private Horario horarioPrueba;
    private Sesion sesionInicial;
    
    @Autowired private EntrenadorRepositorio entrenadorRepositorio; 
    @Autowired private SalaRepositorio salaRepositorio;
    
    private Entrenador entrenadorDummy; 
    private Sala salaDummy;

    @BeforeEach
    void setUp() {
    

        salaDummy = new Sala();
        salaDummy.setNombre("Principal");
        salaDummy.setCapacidad(30);
        salaDummy = salaRepositorio.save(salaDummy); 

        entrenadorDummy = new Entrenador();

        entrenadorDummy = entrenadorRepositorio.save(entrenadorDummy); 



        horarioPrueba = new Horario(LocalDate.now().plusDays(5), LocalTime.of(7, 0), LocalTime.of(8, 0));
        
        sesionInicial = new Sesion();
        sesionInicial.reprogramar(horarioPrueba);
        
 
        sesionInicial.setSala(salaDummy);
        sesionInicial.setEntrenador(entrenadorDummy);
    }


    @Test
    void guardarYBuscarPorId_DeberiaPersistirYRecuperarSesion() {
      
        Sesion sesionGuardada = sesionRepositorio.save(sesionInicial);
        Optional<Sesion> sesionEncontrada = sesionRepositorio.findById(sesionGuardada.getId());

    
        assertTrue(sesionEncontrada.isPresent(), "La sesión guardada debe ser encontrada.");
        assertEquals(sesionGuardada.getId(), sesionEncontrada.get().getId());
    }

    @Test
    void buscarPorId_IdInexistente_DeberiaRetornarOptionalVacio() {
      
        Optional<Sesion> sesionEncontrada = sesionRepositorio.findById(9999);


        assertFalse(sesionEncontrada.isPresent(), "Buscar un ID inexistente debe retornar Optional.empty.");
    }
    

    @Test
    void save_ActualizarEstado_DeberiaPersistirLosCambios() {
        
        Sesion sesionParaActualizar = sesionRepositorio.save(sesionInicial);
        
    
        sesionParaActualizar.confirmar(); 
        
 
        sesionRepositorio.save(sesionParaActualizar);

  
        Sesion sesionVerificada = sesionRepositorio.findById(sesionParaActualizar.getId()).get();
        assertEquals(EstadoSesion.EN_PROGRESO, sesionVerificada.getEstado(), "El estado debe haberse actualizado a EN_PROGRESO.");
    }

 
    @Test
    void deleteById_SesionExistente_DeberiaEliminarRegistro() {
 
        Sesion sesionGuardada = sesionRepositorio.save(sesionInicial);
        

        sesionRepositorio.deleteById(sesionGuardada.getId());


        Optional<Sesion> sesionEliminada = sesionRepositorio.findById(sesionGuardada.getId());
        assertFalse(sesionEliminada.isPresent(), "La sesión debe haber sido eliminada.");
    }
}
