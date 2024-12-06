package com.prototipo.infrastructure.rest.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleSolicitudCotizRequest {

    private Long idSolicitud;

    private Long idDetalleSolicitud;
    private Long nroPaginas;
    private Long nroCopias;
    private Long idUsuarioUnidad;

    private BigDecimal precioUnit;
}
