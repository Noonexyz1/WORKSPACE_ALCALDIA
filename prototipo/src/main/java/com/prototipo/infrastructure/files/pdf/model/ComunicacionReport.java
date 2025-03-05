package com.prototipo.infrastructure.files.pdf.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComunicacionReport {
    private String cite;
    private String funcionarioTo;
    private String funcionarioToCargo;
    private String funcionarioFrom;
    private String funcionarioFromCargo;
    private String nombreOrganizacion;
    private String documentos;
    private Integer totalCopias;
}
