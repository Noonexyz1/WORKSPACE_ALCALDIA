package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.persistence.GeneracionPDFDataAbstract;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;
import com.prototipo.infrastructure.persistence.db.repository.ReportesPDFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class GeneracionPDFDataImpl implements GeneracionPDFDataAbstract {

    @Autowired
    private ReportesPDFRepository reportesPDFRepository;

    @Override
    public List<NotaDePedido> getNotaDePedidoAbstract(Long idSolicitud) {
        List<Object[]> notaDePedido = reportesPDFRepository.getNotaDePedido(idSolicitud);
        List<NotaDePedido> dtosNota = notaDePedido.stream().map(x -> {
            // Convertir Long a Integer explícitamente
            String nombreDocumento = (String) x[0];
            Integer nroPaginas = ((Long) x[1]).intValue();
            Integer nroCopias = ((Long) x[2]).intValue();
            String tamano = (String) x[3];
            String color = (String) x[4];
            String anverRever = (String) x[5];
            Double precioRef = BigDecimal.valueOf((Double) x[6]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            Double precioDocu = BigDecimal.valueOf((Double) x[7]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            return new NotaDePedido(nombreDocumento, nroPaginas, nroCopias, tamano, color, anverRever, precioRef, precioDocu);
        }).toList();

        return dtosNota;
    }

    @Override
    public List<Reporte> generarReporteMensualPDFAbstract(String mesAnio) {
        List<Object[]> reportes = reportesPDFRepository.getListReporteMensual(mesAnio);
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
