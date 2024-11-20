package com.prototipo.infrastructure.rest.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CotizacionRequest {
    private BigDecimal precioTotal;
    private BigDecimal precioUnitario;
    private Long idDetalleSolicitud;
}
