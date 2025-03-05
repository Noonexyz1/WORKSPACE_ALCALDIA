package com.prototipo.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudReport {
    private String funcionarioTo;
    private String funcionarioFrom;
    private String funcionarioToCargo;
    private String funcionarioFromCargo;
    private String cite;
    private String fecha;
    private String nombreOrganizacion;
    private String cantidadSumado;
    private List<SolicitudTablaReport> listReportFotocopias;
}
