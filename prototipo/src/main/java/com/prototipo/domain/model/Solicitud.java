package com.prototipo.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Solicitud {
    private Long id;
    private Long nroDeCopias;
    //private TipoDocumento tipoDeDocumento;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private String estadoSolicitud;
    private String notificacionToAprobar;

    private Unidad unidad;
    private Usuario usuario;
    /*private DPF archivoParaFotocopiar;*/
    private List<ArchivoPdf> listArvhicosPDF;
}
