package com.prototipo.infrastructure.service;

import com.prototipo.infrastructure.rest.report.InformeReport;
import com.prototipo.infrastructure.rest.report.TablaSolicitudReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InformeServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] exportToPdf(InformeReport parametros, List<TablaSolicitudReport> listReport)
            throws JRException, IOException {

        JasperPrint jasperPrint = getReport(parametros, listReport);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport(InformeReport parametros, List<TablaSolicitudReport> listReport)
            throws IOException, JRException {

        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "informe.jrxml";

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de ComunicacionReport a los parámetros del reporte
        params.put("funcionarioTo", parametros.getFuncionarioTo());
        params.put("funcionarioFrom", parametros.getFuncionarioFrom());
        params.put("funcionarioToCargo", parametros.getFuncionarioToCargo());
        params.put("funcionarioFromCargo", parametros.getFuncionarioFromCargo());
        params.put("cite", parametros.getCite());
        params.put("fecha", parametros.getFecha());
        params.put("nombreOrganizacion", parametros.getNombreOrganizacion());
        params.put("imageDir", "classpath:/static/images/");
        params.put("ds", new JRBeanCollectionDataSource(listReport));

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        JasperPrint report = JasperFillManager.fillReport(
                jasperReport,
                params,
                new JREmptyDataSource()
        );

        return report;
    }
}
