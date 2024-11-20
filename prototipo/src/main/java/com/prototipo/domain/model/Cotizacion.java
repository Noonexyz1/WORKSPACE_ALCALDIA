package com.prototipo.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cotizacion {

    private Long id;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotal;

    private DetalleSolicitud fkDetalleSolicitud;
}
