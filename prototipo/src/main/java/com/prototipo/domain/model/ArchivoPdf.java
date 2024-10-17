package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPdf {
    private Long id;
    private String nombreArchivo;
    private String archivo;
    private Solicitud fkSolicitud;
}
