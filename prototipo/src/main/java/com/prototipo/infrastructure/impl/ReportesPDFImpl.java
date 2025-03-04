package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.NotaDePedidoDto;
import com.prototipo.application.modelDto.ReporteDto;
import com.prototipo.application.port.out.ReportesPDFAbstract;
import com.prototipo.infrastructure.persistence.db.repository.ReportesPDFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class ReportesPDFImpl implements ReportesPDFAbstract {

    @Autowired
    private ReportesPDFRepository reportesPDFRepository;

    @Override
    public List<NotaDePedidoDto> getNotaDePedidoAbstract(Long idSolicitud) {
        List<Object[]> notaDePedido = reportesPDFRepository.getNotaDePedido(idSolicitud);
        List<NotaDePedidoDto> dtosNota = notaDePedido.stream().map(x -> {
            // Convertir Long a Integer explícitamente
            String nombreDocumento = (String) x[0];
            Integer nroPaginas = ((Long) x[1]).intValue();
            Integer nroCopias = ((Long) x[2]).intValue();
            String tamano = (String) x[3];
            String color = (String) x[4];
            String anverRever = (String) x[5];
            Double precioRef = BigDecimal.valueOf((Double) x[6]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            Double precioDocu = BigDecimal.valueOf((Double) x[7]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            return new NotaDePedidoDto(nombreDocumento, nroPaginas, nroCopias, tamano, color, anverRever, precioRef, precioDocu);
        }).toList();

        return dtosNota;
    }

    @Override
    public List<ReporteDto> generarReportePDFAbstract(Long idSolicitud) {
        List<Object[]> reportes = reportesPDFRepository.getListReporte(idSolicitud);
        List<ReporteDto> reporteDtos = reportes.stream().map(x -> {
            // Convertir Long a Integer explícitamente
            String nombreDocumento = (String) x[0];
            Integer nroPaginas = ((Long) x[1]).intValue();
            Integer nroCopias = ((Long) x[2]).intValue();
            String tamano = (String) x[3];
            String color = (String) x[4];
            String anverRever = (String) x[5];
            Double precioRef = BigDecimal.valueOf((Double) x[6]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            Double precioDocu = BigDecimal.valueOf((Double) x[7]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            return new ReporteDto(nombreDocumento, nroPaginas, nroCopias, tamano, color, anverRever, precioRef, precioDocu);
        }).toList();

        return reporteDtos;
    }
}
