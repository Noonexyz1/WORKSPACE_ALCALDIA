package com.prototipo.infrastructure.rest.report;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TablaSolicitudReport {
    private String documento;
    private Integer cantidad;
}
