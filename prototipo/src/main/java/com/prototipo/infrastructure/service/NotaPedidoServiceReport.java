package com.prototipo.infrastructure.service;

import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.infrastructure.rest.report.NotaDePedidoReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class NotaPedidoServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SolicitudService solicitudService;

    public byte[] exportToPdf(Long idSolicitud, List<NotaDePedido> notaDePedidoList)
            throws JRException {

        JasperPrint jasperPrint = getReport(idSolicitud, notaDePedidoList);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport(Long idSolicitud, List<NotaDePedido> notaDePedidoList)
            throws JRException {
        //Ruta Relativa, porque es relativo al sistema de archivos del Sistema Operativo
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "notaPedido.jrxml";

        // Formato con nombre del mes completo
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

        Solicitud solicitud = solicitudService.buscarSolicitudService(idSolicitud);

        List<NotaDePedidoReport> listNotaPedidoPDF = notaDePedidoList.stream()
                .map(x -> modelMapper.map(x, NotaDePedidoReport.class))
                .toList();

        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de SolicitudReport a los parámetros del reporte
        params.put("fecha", fechaActual.format(formato));
        params.put("imageDir", "classpath:/static/images/");
        params.put("nombreServicio", solicitud.getNombreServicio());
        params.put("precioTotal", BigDecimal.valueOf(solicitud.getPrecioTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        params.put("ds", new JRBeanCollectionDataSource(listNotaPedidoPDF));

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        return JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
    }
}
