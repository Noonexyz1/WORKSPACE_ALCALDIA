package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPdfDomain {
    private Long id;
    private String archivo;
    private SolicitudDomain fkSolicitud;
}
