package com.prototipo.infrastructure.rest.report;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotaDePedidoReport {
    private String nombreDocumento;
    private Integer nroPaginas;
    private Integer nroCopias;
    private String tamano;
    private String color;
    private String anverRever;
    private Double precioRef;
    private Double precioDocu;
}
