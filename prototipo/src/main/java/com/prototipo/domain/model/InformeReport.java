package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformeReport {
    private String funcionarioTo;
    private String funcionarioFrom;
    private String funcionarioToCargo;
    private String funcionarioFromCargo;
    private String cite;
    private String fecha;
    private String cantidadSumado;
    private String nombreUnidad;
    private String descripcion;
}
