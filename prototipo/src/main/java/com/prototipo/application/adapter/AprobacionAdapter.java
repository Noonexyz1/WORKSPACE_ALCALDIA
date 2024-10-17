package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.domain.model.Aprobacion;

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
        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idSolicitud);
        return mapperApplicationAbstract.mapearAbstract(aprobacionDto, Aprobacion.class);
    }

    @Override
    public List<Aprobacion> listaDeSolicitudesPendientesService(Long idSupervisor, Long page, Long size, String byColumName){
        List<AprobacionDto> aprobacionDtos = aprobacionAbstract
                .listaDeAprobacionesPendientesAbstractPage(idSupervisor, page, size, byColumName);
        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract.mapearAbstract(aprobacionDto, Aprobacion.class))
                .toList();
    }

    @Override
    public List<Aprobacion> listaDeSolicitudesAprobadasService(Long idSupervisor, Long page, Long size, String byColumName) {
        List<AprobacionDto> aprobacionDtos = aprobacionAbstract
                .listaDeAprobacionesAprobadasAbstractPage(idSupervisor, page, size, byColumName);
        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract.mapearAbstract(aprobacionDto, Aprobacion.class))
                .toList();
    }

    @Override
    public List<Aprobacion> listaDeSolicitudesRechazadasService(Long idSupervisor, Long page, Long size, String byColumName) {
        List<AprobacionDto> aprobacionDtos = aprobacionAbstract
                .listaDeAprobacionesRechazadasAbstractPage(idSupervisor, page, size, byColumName);
        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract.mapearAbstract(aprobacionDto, Aprobacion.class))
                .toList();
    }
}
