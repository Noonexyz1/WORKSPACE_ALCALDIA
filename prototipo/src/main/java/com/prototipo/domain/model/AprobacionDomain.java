package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AprobacionDomain {
    private Long id;
    private UsuarioDomain fkSolicitante;
    private SolicitudDomain fkSolicitud;
    private UsuarioDomain fkOperador;
}

