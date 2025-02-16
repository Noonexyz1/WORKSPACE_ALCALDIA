package com.prototipo.infrastructure.service;

import com.prototipo.infrastructure.rest.report.SolicitudReport;
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
public class SolicitudServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] exportToPdf(
            SolicitudReport solicitudReport,
            List<TablaSolicitudReport> listDetalleFotocopiaResp)
            throws JRException{

        JasperPrint jasperPrint = getReport(solicitudReport, listDetalleFotocopiaResp);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport(
            SolicitudReport parametros,
            List<TablaSolicitudReport> listDetalleSolicitudResp)
            throws JRException {

        // Asigna los campos de SolicitudReport a los parámetros del reporte plantilla
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("funcionarioTo", parametros.getFuncionarioTo());
        parameter.put("funcionarioFrom", parametros.getFuncionarioFrom());
        parameter.put("funcionarioToCargo", parametros.getFuncionarioToCargo());
        parameter.put("funcionarioFromCargo", parametros.getFuncionarioFromCargo());
        parameter.put("cite", parametros.getCite());
        parameter.put("fecha", parametros.getFecha());
        parameter.put("nombreOrganizacion", parametros.getNombreOrganizacion());
        parameter.put("imageDir", "classpath:/static/images/");
        parameter.put("ds", new JRBeanCollectionDataSource(listDetalleSolicitudResp));

        //Ruta total para traer la plantilla PDF de Solicitud
        String rutaPlantillaSoliPDF = getRutaPlantillaSolicitudDPF();
        JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantillaSoliPDF);
        return JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
    }

    private String getRutaPlantillaSolicitudDPF(){
        return "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "solicitud.jrxml";
    }
}
