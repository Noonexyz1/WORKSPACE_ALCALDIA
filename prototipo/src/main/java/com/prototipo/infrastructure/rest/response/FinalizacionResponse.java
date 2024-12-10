package com.prototipo.infrastructure.rest.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinalizacionResponse {

    private Long idSoliAutorizada;

    private String nombreCompleto;
    private String nombreUnidad;
    private String nombreCargo;

    private String descripcion;
    private Long totalAutorizado;
    private BigDecimal totalCotizadoBs;

    private Long totalEjecutado;
    private BigDecimal totalEjecutadoBs;
    private String fecha;
}
