package com.prototipo.infrastructure.service;

import com.prototipo.domain.model.Fotocopia;
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
    @Autowired
    private NumeroALiteral numeroALiteral;
    @Autowired
    private DoublesALiteral doublesALiteral;

    public byte[] exportToPdf(List<Fotocopia> listFotocopiaSolicitud)
            throws JRException, IOException {

        List<JasperPrint> jasperPrintList = getReportByList(listFotocopiaSolicitud);
        return exportToPdfByListByJRPdfExporter(jasperPrintList);
    }

    private List<JasperPrint> getReportByList(List<Fotocopia> listFotocopiaSolicitud)
            throws JRException {

        List<JasperPrint> paginasJasperPrints = new ArrayList<>();

        Fotocopia[] detalleFotoVect = listFotocopiaSolicitud
                .toArray(new Fotocopia[0]);

        int marcador = 0;

        //1. Determinar cuantas paginas son necesarias para poder imprimir los datos
        //digamos que son 7 y necesito 2 paginas
        int nroPaginas = (int) Math.ceil(
                (double) listFotocopiaSolicitud.size() / 4
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
                    "orden.jrxml";

            //sabes que, con esta primera pagina, quiero que lo pobles con datos
            Map<String, Object> params = new HashMap<>();
            int j = marcador;
            for (int k = 1; k <= 4 && j < detalleFotoVect.length; k++) {
                // Verificar que j no esté fuera del rango
                if (j < detalleFotoVect.length) {
                    // Asigna los parámetros relacionados con la fotocopia
                    params.put("nroCantidadFotocopia" + k, detalleFotoVect[j].getNroCopias().intValue());
                    params.put("litCantidadFotocopia" + k, numeroALiteral
                            .convertirNumeroALiteral(detalleFotoVect[j].getNroCopias().intValue()));

                    params.put("precio" + k, BigDecimal.valueOf(detalleFotoVect[j].getPrecioDocu()));
                    params.put("literalPrecio" + k, doublesALiteral
                            .convertir(BigDecimal.valueOf(detalleFotoVect[j].getPrecioDocu())));

                    params.put("detalle" + k, detalleFotoVect[j].getNombreDocumento());
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

    public byte[] exportToPdfByListByJRPdfExporter(List<JasperPrint> jasperPrintList)
            throws JRException {

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
