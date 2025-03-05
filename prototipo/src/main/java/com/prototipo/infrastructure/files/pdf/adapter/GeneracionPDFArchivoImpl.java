package com.prototipo.infrastructure.files.pdf.adapter;

import com.prototipo.application.port.out.GeneracionPDFArchivoAbstract;
import com.prototipo.application.util.DoublesALiteral;
import com.prototipo.application.util.NumeroALiteral;
import com.prototipo.domain.model.Fotocopia;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;
import com.prototipo.infrastructure.files.pdf.model.ComunicacionReport;
import com.prototipo.infrastructure.files.pdf.model.SolicitudReport;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;
import net.sf.jasperreports.pdf.SimplePdfReportConfiguration;
import net.sf.jasperreports.pdf.type.PdfVersionEnum;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeneracionPDFArchivoImpl implements GeneracionPDFArchivoAbstract {

    @Override
    @SneakyThrows
    public void generarNotaPedidoPDFAbs(
            Long idSolicitud,
            String fechaFormateada,
            String recursoImagen,
            String nombreServicio,
            Double precioTotalRedondeado,
            List<NotaDePedido> listNotaPedidoPDF,
            InputStream inputStream,
            String pdfOutputDirectory) {

        Map<String, Object> params = new HashMap<>();
        params.put("fecha", fechaFormateada);
        params.put("imageDir", recursoImagen);
        params.put("nombreServicio", nombreServicio);
        params.put("precioTotal", precioTotalRedondeado);
        params.put("ds", new JRBeanCollectionDataSource(listNotaPedidoPDF));

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(inputStream),
                params,
                new JREmptyDataSource()
        );

        // Ruta del archivo PDF
        String outputFilePath = pdfOutputDirectory + "/notaPedido_" + idSolicitud + ".pdf";

        // Exportar el PDF a un archivo
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);

        // Devolver el contenido del PDF como un arreglo de bytes
        //return Files.readAllBytes(Paths.get(outputFilePath));
    }

    @Override
    @SneakyThrows
    public void generarReportePDFAbs(
            Long idSolicitud,
            String fechaActualString,
            String recursoImagen,
            String nombreServicio,
            Double precioTotal,
            Long paginaTotal,
            Long copiaTotal,
            List<Reporte> listReporte,
            InputStream inputStream,
            String pdfOutputDirectory) {

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de SolicitudReport a los parámetros del reporte
        params.put("fecha", fechaActualString);
        params.put("imageDir", recursoImagen);
        params.put("nombreServicio", nombreServicio);
        params.put("precioTotal", precioTotal);
        params.put("paginaTotal", paginaTotal);
        params.put("copiaTotal", copiaTotal);
        params.put("ds", new JRBeanCollectionDataSource(listReporte));

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(inputStream),
                params,
                new JREmptyDataSource()
        );

        // Ruta del archivo PDF
        String outputFilePath = pdfOutputDirectory + "/notaPedido_" + idSolicitud + ".pdf";

        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);
    }

    @Override
    @SneakyThrows
    public void generarOrdenDeFotocopiaPDFAbs(
            List<Fotocopia> listFotocopiaSolicitud,
            String recursoImagen,
            String filePath) {

        Fotocopia[] detalleFotoVect = listFotocopiaSolicitud.toArray(new Fotocopia[0]);

        List<JasperPrint> paginasJasperPrints = new ArrayList<>();

        int marcador = 0;

        //1. Determinar cuantas paginas son necesarias para poder imprimir los datos
        //digamos que son 7 y necesito 2 paginas
        int nroPaginas = (int) Math.ceil((double) listFotocopiaSolicitud.size() / 4);

        //para dos paginas en total, solo debo recorrer 2 paginas
        for (int i = 1; i <= nroPaginas; i++) {

            //sabes que, con esta primera pagina, quiero que lo pobles con datos
            Map<String, Object> params = new HashMap<>();
            int j = marcador;

            for (int k = 1; k <= 4 && j < detalleFotoVect.length; k++) {

                // Verificar que j no esté fuera del rango
                if (j < detalleFotoVect.length) {
                    // Asigna los parámetros relacionados con la fotocopia
                    params.put("nroCantidadFotocopia" + k, detalleFotoVect[j].getNroCopias().intValue());
                    params.put("litCantidadFotocopia" + k, NumeroALiteral
                            .convertirNumeroALiteral(detalleFotoVect[j].getNroCopias().intValue()));

                    params.put("precio" + k, BigDecimal.valueOf(detalleFotoVect[j].getPrecioDocu()).setScale(2, RoundingMode.HALF_UP).doubleValue());

                    params.put("literalPrecio" + k, DoublesALiteral
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

            params.put("imageDir", recursoImagen);

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

        // Este metodo me trae todas las paginas de la OrdenDeFotocopiaReport
        // 1. Crea un flujo de salida en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 2. Configura el exportador PDF
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(paginasJasperPrints)); // Agrega todos los JasperPrint
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
        baos.toByteArray(); // esto me trae los bytes, el pdf en si
    }

    @Override
    @SneakyThrows
    public void generarComunicacionInternaPDFAbs(
            ComunicacionReport comunicacionReport,
            String filePath,
            String recursoString) {

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
        params.put("imageDir", recursoString);

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        JasperPrint report = JasperFillManager.fillReport(
                jasperReport,
                params,
                new JREmptyDataSource()
        );

        JasperExportManager.exportReportToPdf(report);
    }

    @Override
    @SneakyThrows
    public void generarSolicitudDeFotocopiaPDFAbs(
            SolicitudReport solicitudReport,
            String recursoImagen,
            String rutaPlantillaSoliPDF) {

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
        parameter.put("imageDir", recursoImagen);

        JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantillaSoliPDF);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());

        JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
