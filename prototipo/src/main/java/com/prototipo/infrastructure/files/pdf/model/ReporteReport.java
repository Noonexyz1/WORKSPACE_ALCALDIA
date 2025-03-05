package com.prototipo.infrastructure.files.pdf.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteReport {
    private String nombreDocumento;
    private Integer nroPaginas;
    private Integer nroCopias;
    private String tamano;
    private String color;
    private String anverRever;
    private Double precioRef;
    private Double precioDocu;
}
