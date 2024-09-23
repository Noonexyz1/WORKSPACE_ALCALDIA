package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialUsuarioDomain {
    private Long id;
    private String fecha;
    private String detalle;
    private UsuarioDomain usuario;
    private SolicitudDomain solicitud;
}
