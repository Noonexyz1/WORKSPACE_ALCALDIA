package com.prototipo.infrastructure.service;

import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.model.Fotocopia;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.domain.model.UsuarioUnidad;
import com.prototipo.infrastructure.rest.report.SolicitudReport;
import com.prototipo.infrastructure.rest.report.TablaSolicitudReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolicitudServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private SolicitudService solicitudService;

    //@SneakyThrows
    public byte[] exportToPdf(SolicitudReport solicitudReport)
            throws JRException{

        JasperPrint jasperPrint = getReport(solicitudReport);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    //Deberias enviar el Mapa por parametro para reutilizar codigo
    private JasperPrint getReport(SolicitudReport parametros)
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
        parameter.put("cantidadSumado", parametros.getCantidadSumado());
        parameter.put("ds", new JRBeanCollectionDataSource(parametros.getListReportFotocopias()));
        parameter.put("imageDir", "classpath:/static/images/");

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

    public SolicitudReport getObtenerDatosForReport(Long idSolicitud) {
        // Traemos los datos necesarios para el reporte
        Solicitud solicitudResp = solicitudService.buscarSolicitudService(idSolicitud);
        UsuarioUnidad usuarioResponsable = solicitudResp.getFkUsuarioSolicitante().getFkResponsable();
        UsuarioUnidad usuarioSolicitante = solicitudResp.getFkUsuarioSolicitante();
        List<Fotocopia> listFotocopias = solicitudService.listFotocopiaSolicitud(idSolicitud);

        // Mapeamos con los datos obtenidos para exportar el PDF
        List<TablaSolicitudReport> listReportFotocopias = listFotocopias.stream()
                .map(x -> TablaSolicitudReport.builder()
                        .documento(x.getNombreDocumento())
                        .cantidad(x.getNroCopias().intValue())
                        .build())
                .toList();

        return SolicitudReport.builder()
                .funcionarioTo(usuarioResponsable.getFkUsuario().getNombres() + " " +
                        usuarioResponsable.getFkUsuario().getPaterno() + " " +
                        usuarioResponsable.getFkUsuario().getMaterno())
                .funcionarioToCargo(usuarioResponsable.getFkCargo().getNombreCargo())
                .funcionarioFrom(usuarioSolicitante.getFkUsuario().getNombres() + " " +
                        usuarioSolicitante.getFkUsuario().getPaterno() + " " +
                        usuarioSolicitante.getFkUsuario().getMaterno())
                .funcionarioFromCargo(usuarioSolicitante.getFkCargo().getNombreCargo())
                .cite(solicitudResp.getCite())
                .fecha(LocalDate.now().toString())
                .nombreOrganizacion(usuarioSolicitante.getFkUnidad().getNombre())
                .cantidadSumado(solicitudResp.getCopiaTotal() + "")
                .listReportFotocopias(listReportFotocopias)
                .build();
    }
}
