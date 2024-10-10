package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacionSoliRequest {
    private Long idOperacion;
    private Long idOperador;
}
