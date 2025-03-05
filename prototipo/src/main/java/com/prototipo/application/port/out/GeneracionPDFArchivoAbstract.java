package com.prototipo.application.port.out;

import com.prototipo.domain.model.*;

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

    void generarOrdenDeFotocopiaPDFAbs(
            List<Fotocopia> listFotocopia,
            String recursoImagen,
            String filePath);

    void generarComunicacionInternaPDFAbs(
            ComunicacionReport comunicacionReport,
            String filePath,
            String recursoString);

    void generarSolicitudDeFotocopiaPDFAbs(
            SolicitudReport solicitudReport,
            String recursoImagen,
            String rutaPlantillaSoliPDF);

    void generarNotaPedidoPDFAbs2(
            NotaDePedidoReport notaDePedidoReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath);
}
