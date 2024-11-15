package com.prototipo.infrastructure.service;

import com.prototipo.domain.model.DetalleSolicitud;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;
import net.sf.jasperreports.pdf.SimplePdfReportConfiguration;
import net.sf.jasperreports.pdf.type.PdfVersionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdenFotoServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] exportToPdf(List<DetalleSolicitud> listDetalleSolicitudResp)
            throws JRException, IOException {

        List<JasperPrint> jasperPrintList = getReportByList(listDetalleSolicitudResp);
        return exportToPdfByListByJRPdfExporter(jasperPrintList);
    }

    private List<JasperPrint> getReportByList(List<DetalleSolicitud> listDetalleSolicitudResp)
            throws IOException, JRException {

        List<JasperPrint> paginasJasperPrints = new ArrayList<>();

        DetalleSolicitud[] detalleVect = listDetalleSolicitudResp
                .toArray(new DetalleSolicitud[0]);

        int marcador = 0;

        //1. Determinar cuantas paginas son necesarias para poder imprimir los datos
        //digamos que son 7 y necesito 2 paginas
        int nroPaginas = (int) Math.ceil(
                (double) listDetalleSolicitudResp.size() / 4
        );

        //para dos paginas en total, solo debo recorrer 2 paginas
        for (int i = 1; i <= nroPaginas; i++) {
            //para la pagina 1
            //para la primera pagina se va ha mandar estos datos
            String filePath = "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "templates" + File.separator +
                    "report" + File.separator +
                    "orden" + i + ".jrxml";

            //sabes que, con esta primera pagina, quiero que lo pobles con datos
            Map<String, Object> params = new HashMap<>();
            int j = marcador;
            for (int k = 1; k <= 4 && j < detalleVect.length; k++) {
                // Verificar que j no esté fuera del rango
                if (j < detalleVect.length) {
                    // Asigna los parámetros relacionados con la fotocopia
                    params.put("nroCantidadFotocopia" + k, detalleVect[j].getNroCopias().intValue());
                    params.put("precio" + k, new BigDecimal("207.00"));
                    params.put("litCantidadFotocopia" + k, "Un mil trecientos ochenta");
                    params.put("literalPrecio" + k, "Doscientos siete");
                    params.put("detalle" + k, "trabajos comunitarios");
                    j++; // Avanzar al siguiente elemento
                } else {
                    // Si no hay más elementos, puedes asignar valores predeterminados
                    params.put("nroCantidadFotocopia" + k, 0);
                    params.put("precio" + k, new BigDecimal("0.00"));
                    params.put("litCantidadFotocopia" + k, "N/A");
                    params.put("literalPrecio" + k, "N/A");
                    params.put("detalle" + k, "Sin detalle");
                }
            }

            params.put("imageDir", "classpath:/static/images/");

            //ESTE FILE PATH esta guardando con el PATH CORRESPONDIENTE??s?
            JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
            //TRAS HABER CRFEADO EL jaspertReport, pues no inserta de forma correcta el orden2.jrxml

            JasperPrint report = JasperFillManager.fillReport(//DEBO VISUALIZAR ESTA VARIABLE EN LA SIGUIENTE PRUEBA
                    jasperReport,
                    params,
                    new JREmptyDataSource()
            );

            paginasJasperPrints.add(report);//AL ANADIR A LA LISTA, NO PONE EL ORDEN2 COMO JRXML EN LA LISTA
            marcador = j;
        }

        return paginasJasperPrints;
    }

    public byte[] exportToPdfByList(List<JasperPrint> jasperPrintList) throws JRException {
        // 1. Crea un flujo de salida en memoria (no se escribe en disco)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 2. Usa JasperExportManager para exportar todas las páginas de JasperPrint
        for (JasperPrint jasperPrint : jasperPrintList) {
            //Aqui esta el Problema?? porque solo me manda uno, osea el ultimo
            //ESTE METODO SOLO ES CAPAZ DE EXPORTA UN SOLO REPORTE
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        }

        // 3. Convierte el contenido del flujo de salida a un arreglo de bytes
        return baos.toByteArray();
    }

    public byte[] exportToPdfByListByJRPdfExporter(List<JasperPrint> jasperPrintList) throws JRException {
        // 1. Crea un flujo de salida en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 2. Configura el exportador PDF
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList)); // Agrega todos los JasperPrint
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos)); // Define el flujo de salida

        // Opcional: Configuración adicional para el PDF
        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("TuNombre");
        exportConfig.setPdfVersion(PdfVersionEnum.VERSION_1_7);

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        // 3. Exporta todos los JasperPrint en un único PDF
        exporter.exportReport();

        // 4. Convierte el contenido del flujo de salida a un arreglo de bytes
        return baos.toByteArray();
    }

}
