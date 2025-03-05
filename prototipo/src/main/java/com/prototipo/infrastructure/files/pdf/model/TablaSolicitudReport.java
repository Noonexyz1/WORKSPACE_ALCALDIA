package com.prototipo.infrastructure.files.pdf.model;

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
