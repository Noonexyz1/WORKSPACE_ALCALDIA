package com.prototipo.infrastructure.impl;

import com.prototipo.application.port.ResponsablePDFAbstract;
import com.prototipo.infrastructure.rest.report.NotaDePedidoReport;
import com.prototipo.infrastructure.rest.report.ReporteReport;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponsablePDFImpl implements ResponsablePDFAbstract {

    @Override
    @SneakyThrows
    public void generarNotaPedidoPDFAbs(
            Long idSolicitud,
            String fechaFormateada,
            String recursoImagen,
            String nombreServicio,
            Double precioTotalRedondeado,
            List<NotaDePedidoReport> listNotaPedidoPDF,
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
            List<ReporteReport> listReporte,
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
}
