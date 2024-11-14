package com.prototipo.infrastructure.service;

import com.prototipo.domain.model.DetalleSolicitud;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdenFotoServiceReport {

    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] exportToPdf(List<DetalleSolicitud> listDetalleSolicitudResp)
            throws JRException, IOException {

        JasperPrint jasperPrint = getReport(listDetalleSolicitudResp);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperPrint getReport(List<DetalleSolicitud> listDetalleSolicitudResp)
            throws IOException, JRException {

        //Ruta total
        String filePath = "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "templates" + File.separator +
                "report" + File.separator +
                "orden.jrxml";

        //TODO, haciendo el algoritmo, lo que tiene que girar mas rapido son las columnas
        Map<String, Object> params = new HashMap<>();
        int i = 1;
        for (DetalleSolicitud detalle : listDetalleSolicitudResp) {
            // Asigna los parámetros relacionados con la primera fotocopia
            params.put("nroCantidadFotocopia" + i, 1380);
            params.put("precio" + i, new BigDecimal("207.00"));
            params.put("litCantidadFotocopia" + i, "Un mil trecientos ochenta");
            params.put("literalPrecio" + i, "Doscientos siete");
            params.put("detalle" + i, "trabajos comunitarios");
            i++;
            /*el i++ no siempre tiene que ir al final de un bucle ;D como siempre nos ensenaron,
            puede ir donde mas te convenga en el codigo y quiza despues usar continue o beark*/
            if (i > 4) {
                i = 1;
                /*Aqui agregar uan nueva pagina,*/
            }
//          o tambien Rotando de `i` entre 1 y 4 usando módulo sin i++
//          i = (i % 4) + 1;
//          Ejemplo
//             1 % 4 = 1
//             2 % 4 = 2
//             3 % 4 = 3
//             4 % 4 = 0
        }

        //params.put("ds", new JRBeanCollectionDataSource(listReport));
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
