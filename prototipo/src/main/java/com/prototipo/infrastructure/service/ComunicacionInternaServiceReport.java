package com.prototipo.infrastructure.service;

import com.prototipo.infrastructure.rest.report.ComunicacionReport;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ComunicacionInternaServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] exportToPdf(ComunicacionReport parametros)
            throws JRException, IOException {

        JasperPrint jasperPrint = getReport(parametros);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport(ComunicacionReport parametros)
            throws IOException, JRException {

        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "comunicacion.jrxml";

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de ComunicacionReport a los parámetros del reporte
        params.put("funcionarioTo", parametros.getFuncionarioTo());
        params.put("funcionarioFrom", parametros.getFuncionarioFrom());
        params.put("funcionarioToCargo", parametros.getFuncionarioToCargo());
        params.put("funcionarioFromCargo", parametros.getFuncionarioFromCargo());
        params.put("cite", parametros.getCite());
        params.put("nombreOrganizacion", parametros.getNombreOrganizacion());
        params.put("documentos", parametros.getDocumentos());
        params.put("totalCopias", parametros.getTotalCopias());
        params.put("imageDir", "classpath:/static/images/");

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        JasperPrint report = JasperFillManager.fillReport(
                jasperReport,
                params,
                new JREmptyDataSource()
        );

        return report;
    }
}
