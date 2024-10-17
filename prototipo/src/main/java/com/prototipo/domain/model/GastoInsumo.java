package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GastoInsumo {
    private Long id;
    private Solicitud fkSolicitud;
    private Insumo fkInsumo;
}
