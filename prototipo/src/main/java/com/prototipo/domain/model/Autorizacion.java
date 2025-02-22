package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autorizacion {

    private Long id;
    private String fecha;
    private Long finaliFlag;

    private UsuarioUnidad fkUsuarioResponsable;
    private Solicitud fkSolicitud;
}
