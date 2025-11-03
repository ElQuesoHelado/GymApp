package com.soft.gymapp.dominio.sesiones;

import com.soft.gymapp.dominio.usuarios.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SesionTest {

    @Mock
    private Cliente clienteMock;

    private Horario horarioBase;
    private Sesion sesion;

    @BeforeEach
    void setUp() {

        horarioBase = new Horario(LocalDate.now().plusDays(1), LocalTime.NOON, LocalTime.NOON.plusHours(1));
        

        sesion = new Sesion(EstadoSesion.SIN_EMPEZAR, horarioBase);
    }


    @Test
    void confirmar_DeberiaCambiarEstadoAEnProgreso() {

        sesion.confirmar();


        assertEquals(EstadoSesion.EN_PROGRESO, sesion.getEstado(), 
            "El estado de la sesión debe ser EN_PROGRESO después de confirmar.");
    }
    @Test
    void cancelar_DeberiaCambiarEstadoATerminada() {

        sesion.confirmar(); 

        sesion.cancelar();
     
        assertEquals(EstadoSesion.TERMINADA, sesion.getEstado(), 
            "El estado de la sesión debe ser TERMINADA después de cancelar.");
    }

    @Test
    void reprogramar_DeberiaActualizarHorarioYResetearEstado() {
      
        Horario nuevoHorario = new Horario(LocalDate.now().plusWeeks(1), LocalTime.of(8, 0), LocalTime.of(9, 0));
        sesion.confirmar(); 

        sesion.reprogramar(nuevoHorario);

  
        assertEquals(nuevoHorario, sesion.getHorario(), "El horario de la sesión debe ser el nuevo horario.");
        assertEquals(EstadoSesion.SIN_EMPEZAR, sesion.getEstado(), "El estado de la sesión debe resetearse a SIN_EMPEZAR.");
    }
    

    @Test
    void agregarCliente_NuevoCliente_DeberiaAnadirloYLlamarABidireccional() {


  
        sesion.agregarCliente(clienteMock);

        assertTrue(sesion.getClientes().contains(clienteMock), "El cliente debe estar en la lista de clientes.");
        assertEquals(1, sesion.getClientes().size(), "La lista debe tener un cliente.");

        verify(clienteMock, times(1)).agregarSesion(sesion); 
    }


    @Test
    void agregarCliente_ClienteDuplicado_NoDeberiaAnadirlo() {
    
        sesion.agregarCliente(clienteMock); 

 
        sesion.agregarCliente(clienteMock); 


        assertEquals(1, sesion.getClientes().size(), "La lista de clientes no debe aumentar si se agrega el mismo cliente.");
        

        verify(clienteMock, times(1)).agregarSesion(sesion); 
    }
}