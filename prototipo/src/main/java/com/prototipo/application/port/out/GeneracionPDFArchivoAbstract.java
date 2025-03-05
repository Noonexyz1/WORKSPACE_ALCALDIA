package com.prototipo.application.port.out;

import com.prototipo.domain.model.*;

import java.io.InputStream;
import java.util.List;

public interface GeneracionPDFArchivoAbstract {

    void generarOrdenDeFotocopiaPDFAbs(
            List<Fotocopia> listFotocopia,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath);

    void generarComunicacionInternaPDFAbs(
            ComunicacionReport comunicacionReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath);

    void generarSolicitudDeFotocopiaPDFAbs(
            SolicitudReport solicitudReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath);





    void generarNotaPedidoPDFAbs(
            NotaDePedidoReport notaDePedidoReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath);

    void generarReportePDFAbs(
            ReporteReport reporteReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath);
}
