package com.prototipo.infrastructure.http.rest.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FotocopiaResponse {
    private Long id;
    private String nombreDocumento;
    private Long nroPaginas;
    private Long nroCopias;
}
