package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AprobacionSoliRequest {
    private Long idAprobacion;
    private Long idResponsable;
}