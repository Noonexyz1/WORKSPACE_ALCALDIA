package com.prototipo.application.port.out.pdf;

import com.prototipo.domain.model.InformeReport;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;

import java.util.List;

public interface GeneracionPDFDataAbstract {
    // Esta interfaz parece representar un conjuto de metodos para generar reportes
    List<NotaDePedido> getNotaDePedidoAbstract(Long idSolicitud);
    List<Reporte> generarReporteMensualPDFAbstract(String mesAnio);
    InformeReport getInformeReport(Long idSolicitud);
}
