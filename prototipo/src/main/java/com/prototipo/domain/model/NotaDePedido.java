package com.prototipo.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaDePedido {
    private Integer nroCopias; // Para la columna ds.nro_copias
    private String nombreDocumento; // Para la columna ds.nombre_documento
    private BigDecimal precioUnitario; // Para la columna d.precio_unitario
    private BigDecimal precioTotal; // Para la columna d.precio_total
}
