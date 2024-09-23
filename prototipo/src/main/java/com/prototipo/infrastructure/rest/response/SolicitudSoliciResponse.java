package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudSoliciResponse {

    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private String estadoByResponsable;
    //los estados posibles son Pendiente, Aprobada y Rechazada
    private String estadoByOperador;
    private String nombreUnidad;
}
