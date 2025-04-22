package com.prototipo.infrastructure.files.pdf.adapter;

import com.prototipo.application.port.out.pdf.GeneracionPDFArchivoAbstract;
import com.prototipo.application.util.DoublesALiteral;
import com.prototipo.application.util.NumeroALiteral;
import com.prototipo.domain.model.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;
import net.sf.jasperreports.pdf.SimplePdfReportConfiguration;
import net.sf.jasperreports.pdf.type.PdfVersionEnum;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GeneracionPDFArchivoImpl implements GeneracionPDFArchivoAbstract {

    @Override
    @SneakyThrows
    public void generarOrdenDeFotocopiaPDFAbs(
            List<Fotocopia> listFotocopia,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath) {

        // Convertir la lista a un array para facilitar el acceso por índice
        Fotocopia[] detalleFotoVect = listFotocopia.toArray(new Fotocopia[0]);

        // Lista para almacenar todas las páginas del PDF
        List<JasperPrint> paginasJasperPrints = new ArrayList<>();

        int marcador = 0;

        // 1. Determinar cuántas páginas son necesarias para poder imprimir los datos
        int nroPaginas = (int) Math.ceil((double) listFotocopia.size() / 4);

        // Compilar el reporte una sola vez (fuera del bucle)
        JasperReport jasperReport = JasperCompileManager.compileReport(recursoJrxmlPath);

        // 2. Generar cada página
        for (int i = 1; i <= nroPaginas; i++) {
            // Parámetros para la página actual
            Map<String, Object> params = new HashMap<>();
            int j = marcador;

            // Llenar los parámetros con los datos de la página actual
            for (int k = 1; k <= 4 && j < detalleFotoVect.length; k++) {
                // Verificar que j no esté fuera del rango
                if (j < detalleFotoVect.length) {
                    // Asigna los parámetros relacionados con la fotocopia
                    params.put("nroCantidadFotocopia" + k, detalleFotoVect[j].getNroCopias().intValue());
                    params.put("litCantidadFotocopia" + k, NumeroALiteral.convertirNumeroALiteral(detalleFotoVect[j].getNroCopias().intValue()));

                    params.put("precio" + k, BigDecimal.valueOf(detalleFotoVect[j].getPrecioDocu()).setScale(2, RoundingMode.HALF_UP).doubleValue());

                    params.put("literalPrecio" + k, DoublesALiteral.convertirDecimalALiteral(BigDecimal.valueOf(detalleFotoVect[j].getPrecioDocu())));

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

            // Agregar la ruta de la imagen a los parámetros
            params.put("imageDir", recursoImagenPath);

            // Generar la página actual
            JasperPrint report = JasperFillManager.fillReport(
                    jasperReport, // Usar el reporte compilado
                    params,
                    new JREmptyDataSource()
            );

            // Agregar la página a la lista
            paginasJasperPrints.add(report);
            marcador = j;
        }

        // 3. Exportar todas las páginas a un solo PDF
        try (FileOutputStream fos = new FileOutputStream(generacionPdfPath)) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(SimpleExporterInput.getInstance(paginasJasperPrints)); // Agregar todas las páginas
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fos)); // Definir el flujo de salida

            // Opcional: Configuración adicional para el PDF
            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("TuNombre");
            exportConfig.setPdfVersion(PdfVersionEnum.VERSION_1_7);

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);

            // Exportar el PDF
            exporter.exportReport();
        }
    }

    @Override
    @SneakyThrows
    public void generarComunicacionInternaPDFAbs(
            ComunicacionReport comunicacionReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath) {

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de ComunicacionReport a los parámetros del reporte
        params.put("funcionarioTo", comunicacionReport.getFuncionarioTo());
        params.put("funcionarioFrom", comunicacionReport.getFuncionarioFrom());
        params.put("funcionarioToCargo", comunicacionReport.getFuncionarioToCargo());
        params.put("funcionarioFromCargo", comunicacionReport.getFuncionarioFromCargo());
        params.put("cite", comunicacionReport.getCite());
        params.put("nombreOrganizacion", comunicacionReport.getNombreOrganizacion());
        params.put("documentos", comunicacionReport.getDocumentos());
        params.put("totalCopias", comunicacionReport.getTotalCopias());
        params.put("imageDir", recursoImagenPath);

        JasperPrint report = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(recursoJrxmlPath),
                params,
                new JREmptyDataSource()
        );

        JasperExportManager.exportReportToPdfFile(report, generacionPdfPath);
    }

    @Override
    @SneakyThrows
    public void generarSolicitudDeFotocopiaPDFAbs(
            SolicitudReport solicitudReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath) {

        // Asigna los campos de SolicitudReport a los parámetros del reporte plantilla
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("funcionarioTo", solicitudReport.getFuncionarioTo());
        parameter.put("funcionarioFrom", solicitudReport.getFuncionarioFrom());
        parameter.put("funcionarioToCargo", solicitudReport.getFuncionarioToCargo());
        parameter.put("funcionarioFromCargo", solicitudReport.getFuncionarioFromCargo());
        parameter.put("cite", solicitudReport.getCite());
        parameter.put("fecha", solicitudReport.getFecha());
        parameter.put("nombreOrganizacion", solicitudReport.getNombreOrganizacion());
        parameter.put("cantidadSumado", solicitudReport.getCantidadSumado());
        parameter.put("ds", new JRBeanCollectionDataSource(solicitudReport.getListReportFotocopias()));
        parameter.put("imageDir", recursoImagenPath);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(recursoJrxmlPath),
                parameter,
                new JREmptyDataSource()
        );

        JasperExportManager.exportReportToPdfFile(jasperPrint, generacionPdfPath);

    }



    @Override
    @SneakyThrows
    public void generarNotaPedidoPDFAbs(
            NotaDePedidoReport notaDePedidoReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath) {

        Map<String, Object> params = new HashMap<>();
        params.put("fecha", notaDePedidoReport.getFecha());
        params.put("nombreServicio", notaDePedidoReport.getNombreServicio());
        params.put("precioTotal", notaDePedidoReport.getPrecioTotal());
        params.put("ds", new JRBeanCollectionDataSource(notaDePedidoReport.getListNotaPedido()));
        params.put("imageDir", recursoImagenPath);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(recursoJrxmlPath),
                params,
                new JREmptyDataSource()
        );

        // Exportar el PDF a un archivo
        JasperExportManager.exportReportToPdfFile(jasperPrint, generacionPdfPath);

        // Devolver el contenido del PDF como un arreglo de bytes
        //return Files.readAllBytes(Paths.get(outputFilePath));
    }

    @Override
    @SneakyThrows
    public void generarReportePDFAbs(
            ReporteReport reporteReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath) {

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de SolicitudReport a los parámetros del reporte
        params.put("fecha", reporteReport.getFecha());
        params.put("nombreServicio", reporteReport.getNombreServicio());
        params.put("precioTotal", reporteReport.getPrecioTotal());
        params.put("paginaTotal", reporteReport.getPaginaTotal());
        params.put("copiaTotal", reporteReport.getCopiaTotal());
        params.put("ds", new JRBeanCollectionDataSource(reporteReport.getListReporte()));
        params.put("imageDir", recursoImagenPath);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(recursoJrxmlPath),
                params,
                new JREmptyDataSource()
        );

        JasperExportManager.exportReportToPdfFile(jasperPrint, generacionPdfPath);
    }

    @Override
    @SneakyThrows
    public void generarInformeSolicitudPDFAbs(
            InformeReport informeReport,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath,
            String editorContent) {

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de ComunicacionReport a los parámetros del reporte
        params.put("funcionarioTo", informeReport.getFuncionarioTo());
        params.put("funcionarioFrom", informeReport.getFuncionarioFrom());
        params.put("funcionarioToCargo", informeReport.getFuncionarioToCargo());
        params.put("funcionarioFromCargo", informeReport.getFuncionarioFromCargo());
        params.put("cite", informeReport.getCite());
        params.put("fecha", informeReport.getFecha());
        params.put("cantidadSumado", informeReport.getCantidadSumado());
        params.put("nombreUnidad", informeReport.getNombreUnidad());
        params.put("descripcion", informeReport.getDescripcion());
        params.put("imageDir", recursoImagenPath);
        params.put("contend", processHtmlContent(editorContent));

        log.info(processHtmlContent(editorContent));


        JasperPrint report = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(recursoJrxmlPath),
                params,
                new JREmptyDataSource()
        );

        JasperExportManager.exportReportToPdfFile(report, generacionPdfPath);
    }

    private String processHtmlContent(String htmlContent) {
        if (htmlContent == null || htmlContent.isEmpty()) {
            return "";
        }

        // 1. Limpieza básica de HTML
        String processed = htmlContent
                .replaceAll("<p><br></p>", "") // Elimina párrafos vacíos
                .replaceAll("<p>\\s*</p>", "") // Elimina párrafos con solo espacios
                .replaceAll("<br>", "<br/>")   // Cierra tags HTML
                .replaceAll("&nbsp;", " ")     // Reemplaza espacios no rompibles
                .replaceAll("style=\"[^\"]*\"", ""); // Elimina estilos CSS

        // 2. Corrección específica para listas de Quill
        processed = processed
                .replaceAll("<ol>\\s*<li>", "<ol><li>") // Elimina espacios innecesarios
                .replaceAll("</li>\\s*</ol>", "</li></ol>")
                .replaceAll("<ul>\\s*<li>", "<ul><li>")
                .replaceAll("</li>\\s*</ul>", "</li></ul>");

        // 3. Eliminar atributos problemáticos
        processed = processed
                .replaceAll("class=\"[^\"]*\"", "") // Elimina clases
                .replaceAll("data-[^=]*=\"[^\"]*\"", ""); // Elimina data-attributes

        // 4. Corrección de encabezados (si los usas)
        processed = processed
                .replaceAll("<h1[^>]*>", "<strong><span style=\"font-size: large;\">")
                .replaceAll("</h1>", "</span></strong>");

        processed = processed.replace("{\"text\":\"", "").replace("\"}", "");
        return processed;
    }
}
