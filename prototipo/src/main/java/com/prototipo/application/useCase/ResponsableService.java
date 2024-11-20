package com.prototipo.application.useCase;

import com.prototipo.domain.model.Cotizacion;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;
import com.prototipo.infrastructure.rest.request.CotizacionRequest;

import java.util.List;

public interface ResponsableService {
    void aprobarSolicitudService(Long idAprobacion, Long idResponsable);
    void rechazarSolicitudService(Long idAprobacion, Long idResponsable);
    List<NotaDePedido> generarNotaDePedidoPDF(Long idSolicitud);
    List<Reporte> generarReportePDF(Long idSolicitud);
    void guardarCotizacion(Cotizacion x);
}
