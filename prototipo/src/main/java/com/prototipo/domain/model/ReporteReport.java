package com.prototipo.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteReport {
    private String fecha;
    private String nombreServicio;
    private Double precioTotal;
    private Long paginaTotal;
    private Long copiaTotal;
    private List<Reporte> listReporte;
}
