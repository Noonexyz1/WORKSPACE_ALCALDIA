package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleSolicitudDto {
    private Long id;

    private String nombreDocumento;
    private Long nroPaginas;
    private Long nroCopias;

    private String tamanoPagina;
    private String anversoReverso;
    private String colorFotocopia;

    private SolicitudDto fkSolicitud;
}