package com.prototipo.infrastructure.rest.report;

import lombok.*;

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
}
