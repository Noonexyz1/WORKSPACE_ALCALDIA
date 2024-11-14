package com.prototipo.infrastructure.rest.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudRequestPDF {
    private String cite;
    private Long idSolicitante;
    private Long idUnidad;
    private List<RowSolicitud> listSolicitud;
}
