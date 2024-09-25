package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GastoInsumoDto {
    private Long id;
    private SolicitudDto fkSolicitud;
    private InsumoDto fkInsumo;
}
