package com.prototipo.infrastructure.rest.report;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotaDePedidoReport {
    private String detalle;
    private String servicio;
    private Integer cantidad;
    private BigDecimal precioUni;
    private BigDecimal precio;
}
