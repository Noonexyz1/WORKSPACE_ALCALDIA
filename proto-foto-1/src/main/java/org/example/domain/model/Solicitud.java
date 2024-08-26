package org.example.domain.model;

import org.example.domain.enums.EstadoSolicitud;
import org.example.domain.enums.TipoDocumento;
import org.example.domain.enums.Unidad;

public class Solicitud {
    private Long idSolicitud;
    private Long nroDeCopias;
    private TipoDocumento tipoDeDocumento;
    private Long nroDePaginas;
    private Unidad nombreUnidad;
    private EstadoSolicitud estadoSolicitud;
}
