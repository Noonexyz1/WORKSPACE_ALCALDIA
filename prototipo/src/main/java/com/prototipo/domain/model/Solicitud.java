package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    private Long id;
    private String cite;
    private String fecha;
    private String descripcion;
    private Long autoriFlag;
    private UsuarioUnidad fkUsuarioSolicitante;
}
