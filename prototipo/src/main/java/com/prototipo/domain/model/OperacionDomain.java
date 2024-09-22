package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperacionDomain {
    private Long id;
    private String fechaAsignacion;
    private String fechaCompletado;
    private UsuarioDomain usuario;
    private SolicitudDomain solicitud;
}
