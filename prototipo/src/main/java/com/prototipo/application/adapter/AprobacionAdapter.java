package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.AutorizacionAbstract;
import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.domain.model.Autorizacion;
import com.prototipo.domain.model.Finalizacion;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.domain.model.UsuarioUnidad;

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
    public PaginableOut<Solicitud> listaDeSolicitudesPendientesService(PaginableIn paginableIn){

        PaginableOut<SolicitudDto> solicitudDtos = aprobacionAbstract
                .listaDeSolicitudesPendientesAbstractPage(paginableIn);

        PaginableOut<Solicitud> paginableResponse = PaginableOut
                .<Solicitud>builder()
                .content(
                        solicitudDtos.getContent().stream()
                                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                                .toList()
                )
                .totalPages(solicitudDtos.getTotalPages())
                .totalElements(solicitudDtos.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Finalizacion> listaDeSolicitudesFinalizadasService(PaginableIn paginableIn) {

        PaginableOut<FinalizacionDto> finalizacionDtoList = aprobacionAbstract
                .listaDeAprobacionesFinalizadasAbstractPage(paginableIn);

        PaginableOut<Finalizacion> paginableResponse = PaginableOut
                .<Finalizacion>builder()
                .content(
                        finalizacionDtoList.getContent().stream()
                                .map(x -> mapperApplicationAbstract
                                        .mapearAbstract(x, Finalizacion.class))
                                .toList()
                )
                .totalPages(finalizacionDtoList.getTotalPages())
                .totalElements(finalizacionDtoList.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public PaginableOut<Autorizacion> listaDeSolicitudesAutorizadasService(PaginableIn paginableIn) {

        PaginableOut<AutorizacionDto> soliAutorizadas = aprobacionAbstract
                .listaDeSoliAutorizadasAbstractPage(paginableIn);

        PaginableOut<Autorizacion> paginableResponse = PaginableOut
                .<Autorizacion>builder()
                .content(
                        soliAutorizadas.getContent().stream()
                                .map(x -> mapperApplicationAbstract
                                        .mapearAbstract(x, Autorizacion.class))
                                .toList()
                )
                .totalPages(soliAutorizadas.getTotalPages())
                .totalElements(soliAutorizadas.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public Autorizacion findAutorizacionByIdSoliService(Long idSolicitud) {
        AutorizacionDto autorizacionDto = autorizacionAbstract
                .findAutorizacionByIdSoli(idSolicitud);
        return mapperApplicationAbstract
                .mapearAbstract(autorizacionDto, Autorizacion.class);
    }
}
