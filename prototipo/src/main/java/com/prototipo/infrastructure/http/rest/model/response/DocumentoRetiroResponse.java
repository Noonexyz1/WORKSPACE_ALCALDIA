package com.prototipo.infrastructure.http.rest.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentoRetiroResponse {
    private Long id;
    private Long totalCopia;
    private Long totalUsado;
    private Long totalDisponible;

    private FotocopiaResponse fkFotocopia;
}
