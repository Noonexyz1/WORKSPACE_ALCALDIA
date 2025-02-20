package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.AutorizacionAbstract;
import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.domain.model.Autorizacion;
import com.prototipo.domain.model.Finalizacion;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class AprobacionAdapter implements AprobacionService {

    private AprobacionAbstract aprobacionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private AutorizacionAbstract autorizacionAbstract;

    public AprobacionAdapter(
            AprobacionAbstract aprobacionAbstract,
            MapperApplicationAbstract mapperApplicationAbstract,
            AutorizacionAbstract autorizacionAbstract) {

        this.aprobacionAbstract = aprobacionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.autorizacionAbstract = autorizacionAbstract;
    }

    @Override
    public List<Solicitud> listaDeSolicitudesPendientesService(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName){

        List<SolicitudDto> solicitudDtos = aprobacionAbstract
                .listaDeSolicitudesPendientesAbstractPage(
                        idSupervisor,
                        page,
                        size,
                        byColumName
                );

        return solicitudDtos.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
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
    public Autorizacion findAutorizacionByIdSoliService(Long idSolicitud) {
        AutorizacionDto autorizacionDto = autorizacionAbstract
                .findAutorizacionByIdSoli(idSolicitud);
        return mapperApplicationAbstract
                .mapearAbstract(autorizacionDto, Autorizacion.class);
    }
}
