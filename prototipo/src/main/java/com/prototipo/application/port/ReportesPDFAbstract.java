package com.prototipo.application.port;

import com.prototipo.application.modelDto.NotaDePedidoDto;
import com.prototipo.application.modelDto.ReporteDto;

import java.util.List;

public interface ReportesPDFAbstract {
    //TODO, esta itnerfaz parece representar un conjuto de metodos para generar reportes
    List<NotaDePedidoDto> getNotaDePedidoAbstract(Long idSolicitud);
    List<ReporteDto> generarReportePDFAbstract(Long idSolicitud);
}
