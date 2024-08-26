package org.example.domain.model;

import org.example.domain.enums.EstadoSolicitud;
import org.example.domain.enums.Unidad;

import java.util.List;

public class Reporte {
    private Long idReporte;
    private Long nroFotocopias;
    private Unidad nombreUnidad;
    private List<Insumo> insumosUtilizados;
    private EstadoSolicitud estadoSolicitud ;
}
