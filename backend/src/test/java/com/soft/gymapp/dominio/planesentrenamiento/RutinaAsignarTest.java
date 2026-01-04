package com.soft.gymapp.dominio.planesentrenamiento;

import com.soft.gymapp.dominio.membresias.Membresia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RutinaAsignarTest {
    @Test
    void noDebeAsignarRutinaSiMembresiaVencida() {
        Membresia membresia = new Membresia();
        membresia.setVencida(true); // Simula membresía vencida
        Rutina rutina = new Rutina("Piernas", "Fuerza");
        boolean resultado = rutina.asignarRutina(membresia);
        Assertions.assertFalse(resultado, "No debe asignar rutina si la membresía está vencida");
    }
}
