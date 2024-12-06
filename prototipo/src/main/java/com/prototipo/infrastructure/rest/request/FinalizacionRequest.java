package com.prototipo.infrastructure.rest.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinalizacionRequest {
    private Long idAutorizacion;
    private Long totalEjecutado;
    private BigDecimal totalEjecutadoBs;
}
