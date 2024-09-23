package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.enums.EstadoByResponsableEnum;

public class ResponsableAdapter implements ResponsableService {

    //Para que haria otro ResponsableAbstract para esta clase???
    //Si unicamente puedo ADAPTAR una implementacion existente para esta!! ;D
    private SolicitudAbstract solicitudAbstract;

    public ResponsableAdapter(SolicitudAbstract solicitudAbstract) {
        this.solicitudAbstract = solicitudAbstract;
    }

    @Override
    public void aprobarSolicitudService(Long idSolicitud) {
        SolicitudDto solicitudDto = solicitudAbstract.buscarSolicitudAbstract(idSolicitud);
        solicitudDto.setEstadoByResponsable(EstadoByResponsableEnum.APROBADA.getNombre());
        solicitudAbstract.guardarSolicitudAbstract(solicitudDto);
    }

    @Override
    public void rechazarSolicitudService(Long idSolicitud) {
        SolicitudDto solicitudDto = solicitudAbstract.buscarSolicitudAbstract(idSolicitud);
        solicitudDto.setEstadoByResponsable(EstadoByResponsableEnum.RECHAZADA.getNombre());
        solicitudAbstract.guardarSolicitudAbstract(solicitudDto);
    }
}
