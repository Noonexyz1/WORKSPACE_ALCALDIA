package com.prototipo.infrastructure.http.rest.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AprobacionSoliRequest {
    private Long idSolicitud;
    private Long idResponsable;
}
