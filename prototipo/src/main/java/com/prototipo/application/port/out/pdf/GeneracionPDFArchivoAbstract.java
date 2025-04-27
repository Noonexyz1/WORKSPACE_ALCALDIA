package com.prototipo.application.port.out.pdf;

import com.prototipo.domain.model.*;

import java.io.InputStream;
import java.util.List;

public interface GeneracionPDFArchivoAbstract {

     /* Este metodo solo deberia tener un argumento y ese argumento deberia ser la
     de los datos a imprimir en el PDF y demas argumentos deberia ir a la
     infraestrucutura y ser gestionado por ella misma */
     void generarSolicitudDeFotocopiaPDFAbs(
             SolicitudReport solicitudReport,

             InputStream recursoJrxmlPath,
             String recursoImagenPath,
             String generacionPdfPath);

    void generarComunicacionInternaPDFAbs(
            ComunicacionReport comunicacionReport,

            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath);

    void generarInformeSolicitudPDFAbs(
            InformeReport informeReport,

            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath,

            String editorContent);

    void generarOrdenDeFotocopiaPDFAbs(
            List<Fotocopia> listFotocopia,

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
