package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GastoInsumoDomain {
    private Long id;
    private SolicitudDomain fkSolicitud;
    private InsumoDomain fkInsumo;
}
