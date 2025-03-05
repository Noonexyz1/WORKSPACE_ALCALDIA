package com.prototipo.infrastructure.http.rest.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudResponResponse {

    private Long idAutorizacion;
    private Long idFinalizacion;

    private Long idSolicitud;
    private String cite;
    private String fecha;
    private String nomCompleto;
    private String nomCargo;
    private String nombreUnidad;
}
