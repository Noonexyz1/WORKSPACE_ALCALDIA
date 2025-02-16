package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.*;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.enums.EstadoByOperadorEnum;
import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.Cotizacion;
import com.prototipo.domain.model.NotaDePedido;
import com.prototipo.domain.model.Reporte;

import java.util.List;

public class ResponsableAdapter implements ResponsableService {

    //Para que haria otro ResponsableAbstract para esta clase???
    //Si unicamente puedo ADAPTAR una implementacion existente para esta!! ;D
    private SolicitudAbstract solicitudAbstract;
    private AprobacionAbstract aprobacionAbstract;
    private UsuarioAbastract usuarioAbastract;
    private OperacionAbstract operacionAbstract;
    private MapperApplicationAbstract mapperApplication;
    private ReportesPDFAbstract reportesPDFAbstract;

    public ResponsableAdapter(SolicitudAbstract solicitudAbstract,
                              AprobacionAbstract aprobacionAbstract,
                              UsuarioAbastract usuarioAbastract,
                              OperacionAbstract operacionAbstract,
                              MapperApplicationAbstract mapperApplication,
                              ReportesPDFAbstract reportesPDFAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.aprobacionAbstract = aprobacionAbstract;
        this.usuarioAbastract = usuarioAbastract;
        this.operacionAbstract = operacionAbstract;
        this.mapperApplication = mapperApplication;
        this.reportesPDFAbstract = reportesPDFAbstract;
    }

    @Override
    public void aprobarSolicitudService(Long idSolicitud, Long idResponsable) {
        //Esto me debe traer las informacion de la tabla de Aprobacion para que el
        //Responsable cambie el estado de la solicitud
        UsuarioDto usuarioDto = UsuarioDto.builder().id(idResponsable).build();

        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idSolicitud);
        aprobacionDto.setEstadoCambio(true);
        aprobacionDto = aprobacionAbstract.guardarAprobacionAbstract(aprobacionDto);
        aprobacionDto.setId(null);
        aprobacionDto.setEstadoByResponsable(EstadoByResponsableEnum.APROBADA.getNombre());
        aprobacionDto.setFkResponsable(usuarioDto);

        //Guardamos la solicitud con es estado cambiado
        AprobacionDto newAprobacionToOpe = aprobacionAbstract.guardarAprobacionAbstract(aprobacionDto);

        OperacionDto operacionDto = OperacionDto.builder()
                .id(null)
                .estadoByOperador(EstadoByOperadorEnum.PENDIENTE.getNombre())
                .fkSolicitud(newAprobacionToOpe.getFkSolicitud())
                .fkOperador(null)
                .estadoCambio(0)
                .build();

        //Guardamos la solicitud si el estado cambia a aprobado, en la tabla Operacion
        operacionAbstract.guardarOperacion(operacionDto);
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
                .map(x -> mapperApplication.mapearAbstract(x, NotaDePedido.class))
                .toList();
    }

    @Override
    public List<Reporte> generarReportePDF(Long idSolicitud) {
        return reportesPDFAbstract.generarReportePDFAbstract(idSolicitud)
                .stream()
                .map(x -> mapperApplication.mapearAbstract(x, Reporte.class))
                .toList();
    }

    @Override
    public void guardarCotizacion(Cotizacion x) {
        CotizacionDto cotizacionDto = mapperApplication
                .mapearAbstract(x, CotizacionDto.class);
        //solicitudAbstract.guardarCotizacionAbs(cotizacionDto);
    }
}
