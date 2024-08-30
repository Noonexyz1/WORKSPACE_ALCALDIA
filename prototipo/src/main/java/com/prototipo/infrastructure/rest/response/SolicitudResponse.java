package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudResponse {

    private Long id;
    private Long nroDeCopias;
    //private TipoDocumento tipoDeDocumento;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    //private Unidad nombreUnidad;

    //private EstadoSolicitud estadoSolicitud;
    private String estadoSolicitud;
    //private DPF archivoParaFotocopiar;

    //los estados posibles son Pendiente, Aprobada y Rechazada
    private String notificacionToAprobar;
    private Long fk_solicitante;
    private String fk_unidad;
    private String archivoPdf;

}
