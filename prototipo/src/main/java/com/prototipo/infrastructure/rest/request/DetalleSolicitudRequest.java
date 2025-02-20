package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleSolicitudRequest {
    private String nombreDocumento;
    private Long nroPaginas;
    private Long nroCopias;

    private String tamanoPagina;
    private String anversoReverso;
    private String colorFotocopia;
}
