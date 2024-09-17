package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudRequest {

    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;

    private String nombreUnidad;
    private String archivoPdf;

}
