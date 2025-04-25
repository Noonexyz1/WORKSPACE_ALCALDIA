package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.persistence.ReporteAbstract;
import com.prototipo.domain.model.Reporte;
import com.prototipo.infrastructure.persistence.db.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class ReporteImpl implements ReporteAbstract {

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public List<Reporte> generarReporteMensualPDFAbstract(String mesAnio) {
        List<Object[]> reportes = reporteRepository.getListReporteMensual(mesAnio);
        List<Reporte> reporteDtos = reportes.stream().map(x -> {
            // Convertir Long a Integer explícitamente
            String nombreUnidad = (String) x[0];
            String cite = (String) x[1];

            String nombreDocumento = (String) x[2];
            Integer nroPaginas = ((Long) x[3]).intValue();
            Integer nroCopiasExtrac = ((Long) x[4]).intValue();
            String tamano = (String) x[5];
            String color = (String) x[6];
            String anverRever = (String) x[7];
            Double precioRef = BigDecimal.valueOf((Double) x[8]).setScale(2, RoundingMode.HALF_UP).doubleValue();

            Double precioParcial = BigDecimal.valueOf((Double) x[9]).setScale(2, RoundingMode.HALF_UP).doubleValue();

            return new Reporte(
                    nombreUnidad,
                    cite,
                    nombreDocumento,
                    nroPaginas,
                    nroCopiasExtrac,
                    tamano,
                    color,
                    anverRever,
                    precioRef,
                    precioParcial
            );
        }).toList();

        return reporteDtos;
    }
}
