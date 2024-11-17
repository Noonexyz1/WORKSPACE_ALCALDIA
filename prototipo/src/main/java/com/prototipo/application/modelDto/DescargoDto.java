package com.prototipo.application.modelDto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescargoDto {
    private Long id;
    private BigDecimal precioTotal;
    private BigDecimal precioUnitario;

    private UsuarioDto fkResponsable;
    private DetalleSolicitudDto fkDetalleSolicitud;
}
