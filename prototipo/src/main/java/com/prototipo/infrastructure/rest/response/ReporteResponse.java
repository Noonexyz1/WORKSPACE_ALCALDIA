package com.prototipo.infrastructure.rest.response;

import lombok.Builder;

@Builder
public class ReporteResponse {
    private Long id;
    private Long nroFotoRealizadas;
    private String nombreUnidad;
    private String insumosUtilizados;
    private String estadoSolicitudes; //Pendiente / Completada
}
