package com.prototipo.application.modelDto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class NotaDePedidoDto {
    private Integer nroCopias; // Para la columna ds.nro_copias
    private String nombreDocumento; // Para la columna ds.nombre_documento
    private BigDecimal precioUnitario; // Para la columna d.precio_unitario
    private BigDecimal precioTotal; // Para la columna d.precio_total


    public NotaDePedidoDto(Integer nroCopias, String nombreDocumento, BigDecimal precioUnitario, BigDecimal precioTotal) {
        this.nroCopias = nroCopias;
        this.nombreDocumento = nombreDocumento;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
    }

}
