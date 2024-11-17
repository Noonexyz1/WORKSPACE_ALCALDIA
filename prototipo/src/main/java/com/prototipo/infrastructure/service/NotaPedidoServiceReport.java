package com.prototipo.infrastructure.service;

import com.prototipo.domain.enums.ServicioEnum;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.infrastructure.rest.report.NotaDePedidoReport;
import com.prototipo.infrastructure.rest.report.SolicitudReport;
import com.prototipo.infrastructure.rest.report.TablaSolicitudReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotaPedidoServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] exportToPdf(List<NotaDePedido> notaDePedidoList)
            throws JRException, IOException {

        JasperPrint jasperPrint = getReport(notaDePedidoList);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport(List<NotaDePedido> notaDePedidoList)
            throws IOException, JRException {
        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "notaPedido.jrxml";

        List<NotaDePedidoReport> listNotaPedidoPDF = notaDePedidoList.stream()
                .map(x -> NotaDePedidoReport.builder()
                        .detalle(x.getNombreDocumento())
                        .servicio(ServicioEnum.FOTOCOPIA.getNombre())
                        .cantidad(x.getNroCopias())
                        .precioUni(x.getPrecioUnitario())
                        .precio(x.getPrecioTotal())
                        .build()
                )
                .toList();


        Map<String, Object> params = new HashMap<>();
        // Asigna los campos de SolicitudReport a los parámetros del reporte
        params.put("fecha", LocalDate.now().toString());
        params.put("imageDir", "classpath:/static/images/");
        params.put("ds", new JRBeanCollectionDataSource(listNotaPedidoPDF));

        JasperReport jasperReport = JasperCompileManager.compileReport(filePath);

        JasperPrint report = JasperFillManager.fillReport(
                jasperReport,
                params,
                new JREmptyDataSource()
        );

        return report;
    }
}
