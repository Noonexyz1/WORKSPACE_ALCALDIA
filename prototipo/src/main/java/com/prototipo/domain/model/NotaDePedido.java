package com.prototipo.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaDePedido {
    private String nombreDocumento;
    private Integer nroPaginas;
    private Integer nroCopias;
    private String tamano;
    private String color;
    private String anverRever;
    private Double precioRef;
    private Double precioDocu;
}
