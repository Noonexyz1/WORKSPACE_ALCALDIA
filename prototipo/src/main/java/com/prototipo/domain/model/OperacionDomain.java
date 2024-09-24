package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperacionDomain {
    private Long id;
    private String estadoByOperador;
    private UsuarioDomain fkOperador;
    private SolicitudDomain fkSolicitud;
}
