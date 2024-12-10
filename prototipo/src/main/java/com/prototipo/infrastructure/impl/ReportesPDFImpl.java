package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.NotaDePedidoDto;
import com.prototipo.application.modelDto.ReporteDto;
import com.prototipo.application.port.ReportesPDFAbstract;
import com.prototipo.infrastructure.persistence.db.repository.ReportesPDFRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportesPDFImpl implements ReportesPDFAbstract {

    @Autowired
    private ReportesPDFRepository reportesPDFRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<NotaDePedidoDto> getNotaDePedidoAbstract(Long idSolicitud) {
        List<Object[]> notaDePedido = reportesPDFRepository.getNotaDePedido(idSolicitud);
        List<NotaDePedidoDto> dtos = new ArrayList<>();
        for (Object[] result : notaDePedido) {
            // Convertir Long a Integer explícitamente
            Integer nroCopias = ((Long) result[0]).intValue();
            String nombreDocumento = (String) result[1];
            BigDecimal precioUnitario = (BigDecimal) result[2];
            BigDecimal precioTotal = (BigDecimal) result[3];
            NotaDePedidoDto dto = new NotaDePedidoDto(nroCopias, nombreDocumento, precioUnitario, precioTotal);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<ReporteDto> generarReportePDFAbstract(Long idSolicitud) {
        List<Object[]> reportes = reportesPDFRepository.getListReporte(idSolicitud);
        List<ReporteDto> reporteDtos = new ArrayList<>();
        for (Object[] result : reportes) {
            // Convertir Long a Integer explícitamente
            Integer nroCopias = ((Long) result[0]).intValue();
            BigDecimal precioUnitario = (BigDecimal) result[1];
            BigDecimal precioTotal = (BigDecimal) result[2];
            // Crear el DTO
            ReporteDto reporteDto = new ReporteDto(nroCopias, precioUnitario, precioTotal);
            // Agregar el DTO a la lista
            reporteDtos.add(reporteDto);
        }
        return reporteDtos;
    }
}
