package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.*;

import java.sql.SQLOutput;
import java.util.List;

public class SolicitudAdapter implements SolicitudService {

    private SolicitudAbstract solicitudAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private AprobacionAbstract aprobacionAbstract;

    public SolicitudAdapter(SolicitudAbstract solicitudAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract,
                            AprobacionAbstract aprobacionAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.aprobacionAbstract = aprobacionAbstract;
    }

    @Override
    public void solicitarFotocopiarService(Solicitud solicitudDomain, List<DetalleSolicitud> solicitudes) {
        SolicitudDto solicitudDto = mapperApplicationAbstract
                .mapearAbstract(solicitudDomain, SolicitudDto.class);

        //Primero guardamos en la tabla Solitcitud
        SolicitudDto solicitudDtoResp = solicitudAbstract
                .solicitarFotocopiarAbstract(solicitudDto);

        Solicitud solicitudResp = mapperApplicationAbstract
                .mapearAbstract(solicitudDtoResp, Solicitud.class);

        solicitudes.forEach(x -> {
            x.setFkSolicitud(solicitudResp);
            guardadDetalleSolicitudAbstrac(x);
        });
    }

    @Override
    public void registrarFotocopiarService(Solicitud solicitudDomain, List<DetalleSolicitud> list) {
        SolicitudDto solicitudDto = mapperApplicationAbstract
                .mapearAbstract(solicitudDomain, SolicitudDto.class);
        SolicitudDto solicitudResp = solicitudAbstract.solicitarFotocopiarAbstract(solicitudDto);
        Solicitud solicitudDomainResp = mapperApplicationAbstract.mapearAbstract(solicitudResp, Solicitud.class);

        list.forEach(x -> {
            x.setFkSolicitud(solicitudDomainResp);
            guardadDetalleSolicitudAbstrac(x);
        });

    }

    private void guardadDetalleSolicitudAbstrac(DetalleSolicitud solicitud){
        DetalleSolicitudDto detalleSolicitudDto = mapperApplicationAbstract
                .mapearAbstract(solicitud, DetalleSolicitudDto.class);
        solicitudAbstract.guardarRegistroSolicitud(detalleSolicitudDto);
    }


    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdf archivoPdfDomain) {
        ArchivoPdfDto archivoPdfDto = mapperApplicationAbstract.mapearAbstract(archivoPdfDomain, ArchivoPdfDto.class);
        solicitudAbstract.guardarPdfDeLaSolicitudAbstract(archivoPdfDto);
    }

    @Override
    public List<Solicitud> getListaSolicitudesService(Long idUsuarioUnidad, Long page, Long size) {
        List<SolicitudDto> listSoli = solicitudAbstract
                .getListaSolicitudesAbstract(idUsuarioUnidad, page, size);
        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return listSoli.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                .toList();
    }

    @Override
    public void guardarSolicitudService(Solicitud solicitudDomain) {
        //TODO
    }

    @Override
    public Solicitud buscarSolicitudService(Long id) {
        SolicitudDto solicitudDto = solicitudAbstract
                .buscarSolicitudAbstract(id);
        return mapperApplicationAbstract
                .mapearAbstract(solicitudDto, Solicitud.class);
    }

    @Override
    public List<DetalleSolicitud> listDetalleSolicitud(Long idSolicitud) {
         List<DetalleSolicitudDto> list = solicitudAbstract.
                 getListaDetalleSolicitudAbstract(idSolicitud);
        return list.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, DetalleSolicitud.class))
                .toList();
    }

    @Override
    public List<Finalizacion> listFinalizacionSolicitud(Long idFunUni, Long page, Long size) {
        List<FinalizacionDto> listFinDto = solicitudAbstract
                .listFinalizacionSolicitudAbs(idFunUni, page, size);
        return listFinDto.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Finalizacion.class))
                .toList();
    }

    @Override
    public List<DetalleSolicitud> findListDetalleSoliBySolicitudId(Long idSolicitud) {
        List<DetalleSolicitudDto> listDetalleDto = solicitudAbstract
                .findListDetalleSoliBySolicitudIdAbs(idSolicitud);
        return listDetalleDto.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, DetalleSolicitud.class))
                .toList();
    }

    @Override
    public void guardarAutorizacion(Autorizacion autorizacion) {
        AutorizacionDto autorizacionDto = mapperApplicationAbstract
                .mapearAbstract(autorizacion, AutorizacionDto.class);
        solicitudAbstract.guardarAutorizacionAbs(autorizacionDto);
    }

    @Override
    public Autorizacion buscarAutorizacionByIdSoli(Long idSolicitud) {
        AutorizacionDto autorizacionDto = solicitudAbstract
                .buscarAutorizacionByIdSoliAbs(idSolicitud);
        return mapperApplicationAbstract
                .mapearAbstract(autorizacionDto, Autorizacion.class);
    }

    @Override
    public void guardarFinalizacion(Finalizacion finalizacion) {
        FinalizacionDto finalizacionDto = mapperApplicationAbstract
                .mapearAbstract(finalizacion, FinalizacionDto.class);
        solicitudAbstract.guardarFinalizacionAbs(finalizacionDto);
    }
}
