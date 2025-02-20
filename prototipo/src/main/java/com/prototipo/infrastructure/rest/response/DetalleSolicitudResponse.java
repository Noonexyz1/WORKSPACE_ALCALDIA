package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleSolicitudResponse {
    private Long idSolicitud;
    private Long idDetalleSolicitud;
    private String nombreDocumento;
    private Long nroCopias;
    private Long nroPaginas;
    private String tamanoPagina;
    private String anversoReverso;
    private String colorFotocopia;
    private Double precioRef;
    private Double precioDocu;
}
