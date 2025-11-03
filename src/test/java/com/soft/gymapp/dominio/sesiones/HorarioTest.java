package com.soft.gymapp.dominio.sesiones;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HorarioTest {

    private final LocalDateTime AHORA = LocalDateTime.now();
    private final LocalDate FECHA_ACTUAL = AHORA.toLocalDate();
    private final LocalTime HORA_ACTUAL = AHORA.toLocalTime();


    @Test
    void esDisponible_FechaFutura_DeberiaSerTrue() {

        LocalDate fechaManana = FECHA_ACTUAL.plusDays(1);
        LocalTime horaCualquiera = LocalTime.NOON;


        Horario horarioFuturo = new Horario(fechaManana, horaCualquiera, horaCualquiera.plusHours(1));

        assertTrue(horarioFuturo.esDisponible(), "Un horario con fecha futura siempre debe estar disponible.");
    }
    @Test
    void esDisponible_HoraInicioDespuesDeHoraFin_DeberiaSerFalse() {
    
        LocalTime horaInicioTarde = LocalTime.of(10, 0);
        LocalTime horaFinTemprano = LocalTime.of(9, 0);

        LocalDate fechaManana = FECHA_ACTUAL.plusDays(1);
        Horario horarioInvalido = new Horario(fechaManana, horaInicioTarde, horaFinTemprano);

        assertFalse(horarioInvalido.esDisponible(), 
            "El horario debe ser inválido si la hora de inicio es posterior a la hora de fin.");
    }

    @Test
    void esDisponible_FechaPasada_DeberiaSerFalse() {

        LocalDate fechaAyer = FECHA_ACTUAL.minusDays(1);
        LocalTime horaCualquiera = LocalTime.NOON;

        Horario horarioPasado = new Horario(fechaAyer, horaCualquiera, horaCualquiera.plusHours(1));

        assertFalse(horarioPasado.esDisponible(), "Un horario con fecha pasada no debe estar disponible.");
    }

    @Test
    void esDisponible_HoyYHoraFinEsFutura_DeberiaSerTrue() {

        LocalTime horaFinGarantizada = LocalTime.MAX;
        LocalTime horaInicio = LocalTime.MIN;
        Horario horarioPendiente = new Horario(FECHA_ACTUAL, horaInicio, horaFinGarantizada); 


        assertTrue(horarioPendiente.esDisponible(), 
            "El horario debe estar disponible si es hoy y aún no ha terminado.");
    }
    
    @Test
    void esDisponible_HoyYHoraFinEsPasada_DeberiaSerFalse() {

        LocalTime horaPasada = HORA_ACTUAL.minusMinutes(1);
        LocalTime horaInicio = HORA_ACTUAL.minusHours(2);

        Horario horarioTerminado = new Horario(FECHA_ACTUAL, horaInicio, horaPasada);

        assertFalse(horarioTerminado.esDisponible(), 
            "El horario no debe estar disponible si es hoy y ya ha terminado.");
    }


    @Test
    void esDisponible_CamposNulos_DeberiaLanzarIllegalStateException() {

        Horario horarioNulo = new Horario(); 
        

        assertThrows(IllegalStateException.class, horarioNulo::esDisponible, 
            "Debe lanzar IllegalStateException si horaInicio, horaFin o fecha son nulos.");
    }
}