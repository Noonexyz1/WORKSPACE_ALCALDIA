package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aprobacion {
    private Long id;
    private String estadoByResponsable;
    private Boolean estadoCambio;

    private Solicitud fkSolicitud;
    private Usuario fkResponsable;
}
