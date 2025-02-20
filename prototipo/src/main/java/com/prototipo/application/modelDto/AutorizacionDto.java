package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacionDto {

    private Long id;
    private String fecha;
    private Long finaliFlag;

    private UsuarioUnidadDto fkUsuarioResponsable;
    private SolicitudDto fkSolicitud;
}
