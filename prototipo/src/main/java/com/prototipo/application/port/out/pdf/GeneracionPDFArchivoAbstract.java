package com.prototipo.application.port.out.pdf;

import com.prototipo.domain.model.*;

import java.util.List;

public interface GeneracionPDFArchivoAbstract {

     /* Este metodo solo deberia tener un argumento y ese argumento deberia ser la
     de los datos a imprimir en el PDF y demas argumentos deberia ir a la
     infraestrucutura y ser gestionado por ella misma */
     void generarSolicitudDeFotocopiaPDFAbs(
             SolicitudReport solicitudReport,
             Long idDocumento);

    void generarComunicacionInternaPDFAbs(
            ComunicacionReport comunicacionReport,
            Long idDocumento);

    void generarInformeSolicitudPDFAbs(
            InformeReport informeReport,
            Long idDocumento,
            String editorContent);

    //TODO...verificar el codigo circundante de estea implemetancion
    void generarOrdenDeFotocopiaPDFAbs(
            List<Fotocopia> listFotocopia,
            String idDocumentos);



    void generarNotaPedidoPDFAbs(
            NotaDePedidoReport notaDePedidoReport,
            Long idDocumento);

    void generarReportePDFAbs(
            ReporteReport reporteReport,
            String fechaReport);
}
