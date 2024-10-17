package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operacion {
    private Long id;
    private String estadoByOperador;
    private Integer estadoCambio;

    private Solicitud fkSolicitud;
    private Usuario fkOperador;
}
