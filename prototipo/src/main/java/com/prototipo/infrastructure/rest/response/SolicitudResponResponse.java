package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudResponResponse {
    private Long id;
    private Long idSolicitud;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private String estadoByResponsable;
    private String nombreUnidad;
}
