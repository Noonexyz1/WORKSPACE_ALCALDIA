package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AprobacionDomain {
    private Long id;
    private String estadoByResponsable;
    private UsuarioDomain fkResponsable;
    private SolicitudDomain fkSolicitud;
}
