package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPdfDomain {
    private Long id;
    private String nombreArchivo;
    private String archivo;
    private SolicitudDomain fkSolicitud;
}
