package com.prototipo.infrastructure.files.pdf.adapter;

import com.prototipo.application.port.out.GeneracionPDFArchivoAbstract;
import com.prototipo.application.util.DoublesALiteral;
import com.prototipo.application.util.NumeroALiteral;
import com.prototipo.domain.model.*;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
    public void generarOrdenDeFotocopiaPDFAbs(
            List<Fotocopia> listFotocopia,
            InputStream recursoJrxmlPath,
            String recursoImagenPath,
            String generacionPdfPath) {

        Fotocopia[] detalleFotoVect = listFotocopia.toArray(new Fotocopia[0]);

        List<JasperPrint> paginasJasperPrints = new ArrayList<>();

        int marcador = 0;

        //1. Determinar cuantas paginas son necesarias para poder imprimir los datos
        //digamos que son 7 y necesito 2 paginas
        int nroPaginas = (int) Math.ceil((double) listFotocopia.size() / 4);

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

            params.put("imageDir", recursoImagenPath);

            //ESTE FILE PATH esta guardando con el PATH CORRESPONDIENTE??s?
            //TRAS HABER CRFEADO EL jaspertReport, pues no inserta de forma correcta el orden2.jrxml
            JasperPrint report = JasperFillManager.fillReport(//DEBO VISUALIZAR ESTA VARIABLE EN LA SIGUIENTE PRUEBA
                    JasperCompileManager.compileReport(recursoJrxmlPath),
                    params,
                    new JREmptyDataSource()
            );

            paginasJasperPrints.add(report);//AL ANADIR A LA LISTA, NO PONE EL ORDEN2 COMO JRXML EN LA LISTA
            marcador = j;
        }

        // Este metodo me trae todas las paginas de la OrdenDeFotocopiaReport
        // 1. Crea un flujo de salida en memoria

        // 2. Configura el exportador PDF
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(paginasJasperPrints)); // Agrega todos los JasperPrint

        // 3. Define el flujo de salida hacia el archivo en la ruta especificada
        FileOutputStream fos = new FileOutputStream(generacionPdfPath);
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fos)); // Define el flujo de salida

        // 4. Exporta todos los JasperPrint en un único PDF
        exporter.exportReport();

        // 4. Cierra el flujo de salida
        fos.close(); // esto me trae los bytes, el pdf en si

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

        JasperExportManager.exportReportToPdf(report);
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
}
