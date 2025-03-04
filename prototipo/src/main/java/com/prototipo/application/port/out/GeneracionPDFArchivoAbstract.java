package com.prototipo.application.port.out;

import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;

import java.io.InputStream;
import java.util.List;

public interface GeneracionPDFArchivoAbstract {

    void generarNotaPedidoPDFAbs(
            Long idSolicitud,
            String fechaFormateada,
            String recursoImagen,
            String nombreServicio,
            Double precioTotalRedondeado,
            List<NotaDePedido> listNotaPedidoPDF,
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
            List<Reporte> listReporte,
            InputStream inputStream,
            String pdfOutputDirectory);
}
