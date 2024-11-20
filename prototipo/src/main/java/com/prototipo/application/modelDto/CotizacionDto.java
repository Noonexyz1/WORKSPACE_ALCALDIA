package com.prototipo.application.modelDto;

import com.prototipo.domain.model.DetalleSolicitud;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionDto {

    private Long id;
    private BigDecimal precioTotal;
    private BigDecimal precioUnitario;

    private DetalleSolicitudDto fkDetalleSolicitud;
}
