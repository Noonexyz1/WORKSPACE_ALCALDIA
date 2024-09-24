package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.AprobacionDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.enums.EstadoByResponsableEnum;

public class ResponsableAdapter implements ResponsableService {

    //Para que haria otro ResponsableAbstract para esta clase???
    //Si unicamente puedo ADAPTAR una implementacion existente para esta!! ;D
    private SolicitudAbstract solicitudAbstract;
    private AprobacionAbstract aprobacionAbstract;
    private UsuarioAbastract usuarioAbastract;

    public ResponsableAdapter(SolicitudAbstract solicitudAbstract,
                              AprobacionAbstract aprobacionAbstract,
                              UsuarioAbastract usuarioAbastract) {

        this.solicitudAbstract = solicitudAbstract;
        this.aprobacionAbstract = aprobacionAbstract;
        this.usuarioAbastract = usuarioAbastract;
    }

    @Override
    public void aprobarSolicitudService(Long idSolicitud, Long idResponsable) {
        //Esto me debe traer las informacion de la tabla de Aprobacion para que el
        //Responsable cambie el estado de la solicitud
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idResponsable);

        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idSolicitud);
        //Para nuevo registro
        aprobacionDto.setId(null);
        aprobacionDto.setFkResponsable(usuarioDto);
        aprobacionDto.setEstadoByResponsable(EstadoByResponsableEnum.APROBADA.getNombre());
        aprobacionAbstract.guardarAprobacionAbstract(aprobacionDto);

        //Una ves que el Responsable apruebe la solicitud, esta debe pasar a la
        //tabla de Operacion

    }

    @Override
    public void rechazarSolicitudService(Long idSolicitud, Long idResponsable) {
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idResponsable);

        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idSolicitud);
        //Para nuevo registro
        aprobacionDto.setId(null);
        aprobacionDto.setFkResponsable(usuarioDto);
        aprobacionDto.setEstadoByResponsable(EstadoByResponsableEnum.RECHAZADA.getNombre());
        aprobacionAbstract.guardarAprobacionAbstract(aprobacionDto);
    }
}
