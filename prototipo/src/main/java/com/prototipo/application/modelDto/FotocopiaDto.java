package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FotocopiaDto {
    private Long id;
    private String nombreDocumento;
    private Long nroPaginas;
    private Long nroCopias;

    private Double precioDocu;

    private SolicitudDto fkSolicitud;
    private ServicioFotocopiaDto fkServicioFotocopia;
}
