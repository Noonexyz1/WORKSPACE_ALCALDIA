package com.prototipo.infrastructure.http.rest.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentoRetiroRequest {
    private Long totalCopia;
    private Long totalUsado;
    private Long totalDisponible;

    private Long nroRetiro;
    private Long idDocumento;
}
