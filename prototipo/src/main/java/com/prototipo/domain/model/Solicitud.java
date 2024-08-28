package com.prototipo.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Solicitud {
    private Long id;
    private Long nroDeCopias;
    //private TipoDocumento tipoDeDocumento;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    //private Unidad nombreUnidad;
    //private EstadoSolicitud estadoSolicitud;
    /*private DPF archivoParaFotocopiar;*/
    //private Notificacion notificacionToAprobar;

    private Usuario usuarioEnvio;
}
