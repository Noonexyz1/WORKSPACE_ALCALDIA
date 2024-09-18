package com.prototipo.application.modelDto;

import com.prototipo.infrastructure.persistence.db.entity.*;
import lombok.*;

@Getter
@Builder
public class SolicitudDto {

    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private String estadoSolicitud;
    private String notificacionToAprobar;
    private Usuario fk_solicitante;
    private Unidad fk_unidad;

    //De momento, unicamente hare la prueba con insertar un solo archivo
    //en base64
    private ArchivoPdf archivoPdf;
}
