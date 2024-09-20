package com.prototipo.application.modelDto;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class SolicitudDto {

    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private String estadoSolicitud;
    private String notificacionToAprobar;
    private UsuarioDto solicitante;
    private UnidadDto unidad;

    //De momento, unicamente hare la prueba con insertar un solo archivo
    //en base64
    private List<ArchivoPdfDto> archivosPdf;
}