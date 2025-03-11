package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudTablaReport {
    private String documento;
    private Integer cantidad;
}
