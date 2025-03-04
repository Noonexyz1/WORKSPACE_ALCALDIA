package com.prototipo.application.port.out;

import com.prototipo.infrastructure.rest.report.NotaDePedidoReport;
import com.prototipo.infrastructure.rest.report.ReporteReport;

import java.io.InputStream;
import java.util.List;

public interface ResponsablePDFAbstract {

    void generarNotaPedidoPDFAbs(
            Long idSolicitud,
            String fechaFormateada,
            String recursoImagen,
            String nombreServicio,
            Double precioTotalRedondeado,
            List<NotaDePedidoReport> listNotaPedidoPDF,
            InputStream inputStream,
            String pdfOutputDirectory);

    void generarReportePDFAbs(
            Long idSolicitud,
            String fechaActualString,
            String recursoImagen,
            String nombreServicio,
            Double precioTotal,
            Long paginaTotal,
            Long copiaTotal,
            List<ReporteReport> listReporte,
            InputStream inputStream,
            String pdfOutputDirectory);
}
