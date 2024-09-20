package com.prototipo.infrastructure.rest.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudRequest {

    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private Long idUnidad;
    private Long idSolicitante;
    private List<String> archivosPdf;
}
