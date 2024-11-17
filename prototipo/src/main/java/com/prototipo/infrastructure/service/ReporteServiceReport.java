package com.prototipo.infrastructure.service;

import com.prototipo.domain.model.Reporte;
import com.prototipo.infrastructure.rest.report.ReporteReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] exportToPdf(List<Reporte> listReport)
            throws JRException, IOException {

        JasperPrint jasperPrint = getReport(listReport);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport(List<Reporte> listReport)
            throws IOException, JRException {
        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "reporte.jrxml";

        List<ReporteReport> listReporte = listReport.stream()
                .map(x -> ReporteReport.builder()
                        .cantidad(x.getNroCopias())
                        .precioUni(x.getPrecioUnitario())
                        .costo(x.getPrecioTotal())
                        .build()
                )
                .toList();

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de SolicitudReport a los parámetros del reporte
        params.put("fecha", LocalDate.now().toString());
        params.put("imageDir", "classpath:/static/images/");

        params.put("totalAutorizado", 100);
        params.put("totalEjecutado", 80);
        params.put("totalAutorizadoBs", new BigDecimal("1000.50"));
        params.put("totalEjecutadoBs", new BigDecimal("800.40"));

        params.put("ds", new JRBeanCollectionDataSource(listReporte));

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        JasperPrint report = JasperFillManager.fillReport(
                jasperReport,
                params,
                new JREmptyDataSource()
        );

        return report;
    }
}
