package com.prototipo.infrastructure.service;

import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.model.Reporte;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.infrastructure.rest.report.ReporteReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ReporteServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private SolicitudService solicitudService;
    @Autowired
    private ModelMapper modelMapper;

    public byte[] exportToPdf(Long idSolicitud, List<Reporte> listReport)
            throws JRException, IOException {

        JasperPrint jasperPrint = getReport(idSolicitud, listReport);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport(Long idSolicitud, List<Reporte> listReport)
            throws IOException, JRException {
        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "reporte.jrxml";

        // Formato con nombre del mes completo
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

        Solicitud solicitud = solicitudService.buscarSolicitudService(idSolicitud);

        List<ReporteReport> listReporte = listReport.stream()
                .map(x -> modelMapper.map(x, ReporteReport.class))
                .toList();

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de SolicitudReport a los parámetros del reporte
        params.put("fecha", fechaActual.format(formato));
        params.put("imageDir", "classpath:/static/images/");
        params.put("nombreServicio", solicitud.getNombreServicio());
        params.put("precioTotal", BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());

        params.put("paginaTotal", solicitud.getPaginaTotal());
        params.put("copiaTotal", solicitud.getCopiaTotal());

        params.put("ds", new JRBeanCollectionDataSource(listReporte));

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        return JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
    }
}
