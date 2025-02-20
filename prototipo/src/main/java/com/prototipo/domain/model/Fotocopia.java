package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fotocopia {
    private Long id;
    private String nombreDocumento;
    private Long nroPaginas;
    private Long nroCopias;

    private Double precioDocu;

    private Solicitud fkSolicitud;
    private ServicioFotocopia fkServicioFotocopia;
}
