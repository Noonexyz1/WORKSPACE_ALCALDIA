package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDomain {
    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private UsuarioDomain fkSolicitante;
    private UnidadDomain fkUnidad;
}
