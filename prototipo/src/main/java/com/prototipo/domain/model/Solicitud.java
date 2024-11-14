package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;

    private String cite;

    private Usuario fkSolicitante;
    private Usuario fkResponsable;
    private Unidad fkUnidad;
}
