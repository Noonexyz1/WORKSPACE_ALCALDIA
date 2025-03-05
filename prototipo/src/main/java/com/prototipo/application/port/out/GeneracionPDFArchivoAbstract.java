package com.prototipo.application.port.out;

import com.prototipo.domain.model.Fotocopia;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;
import com.prototipo.infrastructure.files.pdf.model.ComunicacionReport;
import com.prototipo.infrastructure.files.pdf.model.SolicitudReport;

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
}
