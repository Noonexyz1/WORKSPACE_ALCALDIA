package com.prototipo.application.useCase;

import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;

import java.util.List;

public interface ResponsableService {
    void rechazarSolicitudService(Long idAprobacion, Long idResponsable);
    List<NotaDePedido> generarNotaDePedidoPDF(Long idSolicitud);
    List<Reporte> generarReportePDF(Long idSolicitud);
}
