package com.soft.gymapp.servicios.dto;

import java.util.Date;

public record PagoMembresiaDTO(
        String id,
        float monto,
        Date fechaPago
) {}