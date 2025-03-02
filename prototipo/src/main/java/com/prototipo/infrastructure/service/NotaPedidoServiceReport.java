package com.prototipo.infrastructure.service;

import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.infrastructure.rest.report.NotaDePedidoReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class NotaPedidoServiceReport {

    @Value("${pdf.output.directory}")
    private String pdfOutputDirectory;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SolicitudService solicitudService;

    public byte[] exportToPdf(Long idSolicitud, List<NotaDePedido> notaDePedidoList) throws JRException, IOException {
        JasperPrint jasperPrint = getReport(idSolicitud, notaDePedidoList);

        // Crear el directorio si no existe
        File outputDir = new File(pdfOutputDirectory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Ruta del archivo PDF
        String outputFilePath = pdfOutputDirectory + "/notaPedido_" + idSolicitud + ".pdf";

        // Exportar el PDF a un archivo
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);

        // Devolver el contenido del PDF como un arreglo de bytes
        return Files.readAllBytes(Paths.get(outputFilePath));
    }

    private JasperPrint getReport(Long idSolicitud, List<NotaDePedido> notaDePedidoList) throws JRException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/report/notaPedido.jrxml");

        if (inputStream == null) {
            throw new RuntimeException("No se pudo encontrar el archivo notaPedido.jrxml en el classpath.");
        }

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

        Solicitud solicitud = solicitudService.buscarSolicitudService(idSolicitud);

        List<NotaDePedidoReport> listNotaPedidoPDF = notaDePedidoList.stream()
                .map(x -> modelMapper.map(x, NotaDePedidoReport.class))
                .toList();

        Map<String, Object> params = new HashMap<>();
        params.put("fecha", fechaActual.format(formato));
        params.put("imageDir", "classpath:/static/images/");
        params.put("nombreServicio", solicitud.getNombreServicio());
        params.put("precioTotal", BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        params.put("ds", new JRBeanCollectionDataSource(listNotaPedidoPDF));

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        return JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
    }
}