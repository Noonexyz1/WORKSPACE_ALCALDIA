package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GastoInsumoDto {

    private Long id;
    private SolicitudDto fkSolicitud;
    private InsumoDto fkInsumo;
}
