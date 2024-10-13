package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.ResponsableAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.AprobacionService;
import com.prototipo.domain.model.AprobacionDomain;
import com.prototipo.domain.model.SolicitudDomain;
import com.prototipo.infrastructure.persistence.db.entity.Solicitud;

import java.util.ArrayList;
import java.util.List;

public class AprobacionAdapter implements AprobacionService {

    private AprobacionAbstract aprobacionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private ResponsableAbstract responsableAbstract;
    private UsuarioAbastract usuarioAbastract;
    private SolicitudAbstract solicitudAbstract;

    public AprobacionAdapter(AprobacionAbstract aprobacionAbstract,
                             MapperApplicationAbstract mapperApplicationAbstract,
                             ResponsableAbstract responsableAbstract,
                             UsuarioAbastract usuarioAbastract,
                             SolicitudAbstract solicitudAbstract) {

        this.aprobacionAbstract = aprobacionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.responsableAbstract = responsableAbstract;
        this.usuarioAbastract = usuarioAbastract;
        this.solicitudAbstract = solicitudAbstract;
    }

    @Override
    public AprobacionDomain findAprovacionByIdSoliService(Long idSolicitud) {
        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idSolicitud);
        return mapperApplicationAbstract.mapearAbstract(aprobacionDto, AprobacionDomain.class);
    }

    @Override
    public List<AprobacionDomain> listaDeSolicitudesPendientesService(Long idSupervisor, Long page, Long size, String byColumName){
        List<AprobacionDto> aprobacionDtos = aprobacionAbstract
                .listaDeAprobacionesPendientesAbstractPage(idSupervisor, page, size, byColumName);
        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract.mapearAbstract(aprobacionDto, AprobacionDomain.class))
                .toList();
    }

    @Override
    public List<AprobacionDomain> listaDeSolicitudesAprobadasService(Long idSupervisor, Long page, Long size, String byColumName) {
        List<AprobacionDto> aprobacionDtos = aprobacionAbstract
                .listaDeAprobacionesAprobadasAbstractPage(idSupervisor, page, size, byColumName);
        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract.mapearAbstract(aprobacionDto, AprobacionDomain.class))
                .toList();
    }

    @Override
    public List<AprobacionDomain> listaDeSolicitudesRechazadasService(Long idSupervisor, Long page, Long size, String byColumName) {
        List<AprobacionDto> aprobacionDtos = aprobacionAbstract
                .listaDeAprobacionesRechazadasAbstractPage(idSupervisor, page, size, byColumName);
        return aprobacionDtos.stream()
                .map(aprobacionDto -> mapperApplicationAbstract.mapearAbstract(aprobacionDto, AprobacionDomain.class))
                .toList();
    }
}
