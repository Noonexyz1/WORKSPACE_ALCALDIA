package com.prototipo.application.modelDto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDto {
    private Integer nroCopias; // Para la columna ds.nro_copias
    private BigDecimal precioUnitario; // Para la columna d.precio_unitario
    private BigDecimal precioTotal; // Para la columna d.precio_total
}
