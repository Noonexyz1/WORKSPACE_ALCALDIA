package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDto {
    private Long id;
    private String cite;
    private String fecha;
    private String descripcion;
    private UsuarioUnidadDto fkUsuarioSolicitante;
}
