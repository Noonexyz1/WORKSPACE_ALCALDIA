package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudOperaResponse {
    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private String nombreUnidad;
    private String estadoByResponsable;
    private String estadoByOperador;
}
