package com.prototipo.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDomain {
    private Long idReporte;
    private Long nroFotocopias;
    private UnidadDomain nombreUnidad;
    private List<InsumoDomain> insumosUtilizados;
    private String estadoSolicitud ;
}
