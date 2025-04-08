package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {
    private String nombreUnidad;
    private String cite;

    private String nombreDocumento;
    private Integer nroPaginas;
    private Integer nroCopiasExtrac;
    private String tamano;
    private String color;
    private String anverRever;
    private Double precioRef;

    private Double precioParcial;
}
