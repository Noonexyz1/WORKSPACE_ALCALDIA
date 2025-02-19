package com.prototipo.application.modelDto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaDePedidoDto {
    private String nombreDocumento;
    private Integer nroPaginas;
    private Integer nroCopias;
    private String tamano;
    private String color;
    private String anverRever;
    private Double precioRef;
    private Double precioDocu;
}
