package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AprobacionDto {

    private Long id;
    private UsuarioDto fkSolicitante;
    private SolicitudDto fkSolicitud;
    private UsuarioDto fkOperador;
}

