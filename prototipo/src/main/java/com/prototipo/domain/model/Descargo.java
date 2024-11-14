package com.prototipo.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Descargo {
    private Long id;
    private BigDecimal precioTotal;
    private BigDecimal precioUnitario;

    private Usuario fkResponsable;
    private DetalleSolicitud fkDetalleSolicitud;
}
