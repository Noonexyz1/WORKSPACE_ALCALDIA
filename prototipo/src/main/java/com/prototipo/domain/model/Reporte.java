package com.prototipo.domain.model;

import com.prototipo.domain.enums.EstadoSolicitud;
import com.prototipo.domain.enums.Unidad;

import java.util.List;

public class Reporte {
    private Long idReporte;
    private Long nroFotocopias;
    private Unidad nombreUnidad;
    private List<Insumo> insumosUtilizados;
    private EstadoSolicitud estadoSolicitud ;
}
