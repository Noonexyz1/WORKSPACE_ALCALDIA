package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AprobacionDto {

    private Long id;
    private UsuarioDto fkSolicitante;
    private SolicitudDto fkSolicitud;
    private UsuarioDto fkOperador;
}

