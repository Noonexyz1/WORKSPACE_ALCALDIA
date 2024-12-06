package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.domain.model.Aprobacion;
import com.prototipo.domain.model.Autorizacion;
import com.prototipo.domain.model.Finalizacion;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class AprobacionAdapter implements AprobacionService {

    private AprobacionAbstract aprobacionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private UsuarioAbastract usuarioAbastract;
    private SolicitudAbstract solicitudAbstract;

    public AprobacionAdapter(AprobacionAbstract aprobacionAbstract,
                             MapperApplicationAbstract mapperApplicationAbstract,
                             UsuarioAbastract usuarioAbastract,
                             SolicitudAbstract solicitudAbstract) {

        this.aprobacionAbstract = aprobacionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.usuarioAbastract = usuarioAbastract;
        this.solicitudAbstract = solicitudAbstract;
    }

    @Override
    public Aprobacion findAprovacionByIdSoliService(Long idSolicitud) {
        AprobacionDto aprobacionDto = aprobacionAbstract
                .findAprovacionByIdSoliAbstract(idSolicitud);
        return mapperApplicationAbstract
                .mapearAbstract(aprobacionDto, Aprobacion.class);
    }

    @Override
    public List<Solicitud> listaDeSolicitudesPendientesService(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName){

        List<SolicitudDto> solicitudDtos = aprobacionAbstract
                .listaDeAprobacionesPendientesAbstractPage(
                        idSupervisor,
                        page,
                        size,
                        byColumName);

        return solicitudDtos.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                .toList();
    }

    @Override
    public List<Aprobacion> listaDeSolicitudesAprobadasService(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName) {

        List<AprobacionDto> aprobacionDtos = aprobacionAbstract
                .listaDeAprobacionesAprobadasAbstractPage(
                        idSupervisor,
                        page,
                        size,
                        byColumName);

        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract
                        .mapearAbstract(aprobacionDto, Aprobacion.class))
                .toList();
    }

    @Override
    public List<Aprobacion> listaDeSolicitudesRechazadasService(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName) {

        List<AprobacionDto> aprobacionDtos = aprobacionAbstract
                .listaDeAprobacionesRechazadasAbstractPage(
                        idSupervisor,
                        page,
                        size,
                        byColumName);

        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract
                        .mapearAbstract(aprobacionDto, Aprobacion.class))
                .toList();
    }

    @Override
    public List<Finalizacion> listaDeSolicitudesFinalizadasService(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName) {

        List<FinalizacionDto> finalizacionDtoList = aprobacionAbstract
                .listaDeAprobacionesFinalizadasAbstractPage(
                        idSupervisor,
                        page,
                        size,
                        byColumName);

        return finalizacionDtoList.stream()
                .map(x -> mapperApplicationAbstract
                        .mapearAbstract(x, Finalizacion.class))
                .toList();
    }

    @Override
    public List<Autorizacion> listaDeSolicitudesAutorizadasService(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName) {

        List<AutorizacionDto> soliAutorizadas = aprobacionAbstract
                .listaDeSoliAutorizadasAbstractPage(
                        idSupervisor,
                        page,
                        size,
                        byColumName);

        return soliAutorizadas.stream()
                .map(x -> mapperApplicationAbstract
                        .mapearAbstract(x, Autorizacion.class))
                .toList();
    }

    @Override
    public Autorizacion findAutorizacionById(Long idAutorizacion) {
        AutorizacionDto autorizacionDto = aprobacionAbstract
                .findAutorizacionByIdAbstract(idAutorizacion);
        return mapperApplicationAbstract
                .mapearAbstract(
                        autorizacionDto,
                        Autorizacion.class
                );
    }

    @Override
    public void guardarAutorizacionService(Autorizacion autorizacion) {
        AutorizacionDto autorizacionDto = mapperApplicationAbstract
                .mapearAbstract(autorizacion, AutorizacionDto.class);
        aprobacionAbstract.guardarAutorizacionAbstract(autorizacionDto);
    }
}
