package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.*;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;

import java.util.List;

public class ResponsableAdapter implements ResponsableService {

    //Para que haria otro ResponsableAbstract para esta clase???
    //Si unicamente puedo ADAPTAR una implementacion existente para esta!! ;D
    private SolicitudAbstract solicitudAbstract;
    private MapperApplicationAbstract mapperApplication;
    private ReportesPDFAbstract reportesPDFAbstract;

    public ResponsableAdapter(
            SolicitudAbstract solicitudAbstract,
            MapperApplicationAbstract mapperApplication,
            ReportesPDFAbstract reportesPDFAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.mapperApplication = mapperApplication;
        this.reportesPDFAbstract = reportesPDFAbstract;
    }

    @Override
    public void rechazarSolicitudService(Long idSolicitud, Long idResponsable) {
        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudByIdAbstract(idSolicitud);
        solicitudDto.setIsActive(false);
        solicitudAbstract.guardarSolicitudAbstract(solicitudDto);
    }

    @Override
    public List<NotaDePedido> generarNotaDePedidoPDF(Long idSolicitud) {
        List<NotaDePedidoDto> notaDePedidoDtoList = reportesPDFAbstract
                .getNotaDePedidoAbstract(idSolicitud);

        return notaDePedidoDtoList.stream()
                .map(x ->
                        mapperApplication.mapearAbstract(x, NotaDePedido.class))
                .toList();
    }

    @Override
    public List<Reporte> generarReportePDF(Long idSolicitud) {
        return reportesPDFAbstract.generarReportePDFAbstract(idSolicitud)
                .stream()
                .map(x -> mapperApplication.mapearAbstract(x, Reporte.class))
                .toList();
    }
}
