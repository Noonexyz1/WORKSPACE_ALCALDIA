package com.prototipo.application.port.out;

import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;

import java.util.List;

public interface GeneracionPDFDataAbstract {
    // Esta interfaz parece representar un conjuto de metodos para generar reportes
    List<NotaDePedido> getNotaDePedidoAbstract(Long idSolicitud);
    List<Reporte> generarReportePDFAbstract(Long idSolicitud);
}
