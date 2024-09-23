package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GastoInsumoDomain {
    private Long id;
    private SolicitudDomain fkSolicitud;
    private InsumoDomain fkInsumo;
}
