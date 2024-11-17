package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.NotaDePedidoDto;
import com.prototipo.application.modelDto.ReporteDto;
import com.prototipo.application.port.ReportesPDFAbstract;
import com.prototipo.infrastructure.persistence.db.repository.ReportesPDFRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportesPDFImpl implements ReportesPDFAbstract {

    @Autowired
    private ReportesPDFRepository reportesPDFRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<NotaDePedidoDto> getNotaDePedidoAbstract(Long idSolicitud) {
        return reportesPDFRepository.getNotaDePedido(idSolicitud);
    }

    @Override
    public List<ReporteDto> generarReportePDFAbstract(Long idSolicitud) {
        return reportesPDFRepository.getListReporte(idSolicitud);
    }
}
