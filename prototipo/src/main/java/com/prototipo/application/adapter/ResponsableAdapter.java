package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.AprobacionDto;
import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.ResponsableService;
import com.prototipo.domain.enums.EstadoByOperadorEnum;
import com.prototipo.domain.enums.EstadoByResponsableEnum;

public class ResponsableAdapter implements ResponsableService {

    //Para que haria otro ResponsableAbstract para esta clase???
    //Si unicamente puedo ADAPTAR una implementacion existente para esta!! ;D
    private SolicitudAbstract solicitudAbstract;
    private AprobacionAbstract aprobacionAbstract;
    private UsuarioAbastract usuarioAbastract;
    private OperacionAbstract operacionAbstract;

    public ResponsableAdapter(SolicitudAbstract solicitudAbstract,
                              AprobacionAbstract aprobacionAbstract,
                              UsuarioAbastract usuarioAbastract,
                              OperacionAbstract operacionAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.aprobacionAbstract = aprobacionAbstract;
        this.usuarioAbastract = usuarioAbastract;
        this.operacionAbstract = operacionAbstract;
    }

    @Override
    public void aprobarSolicitudService(Long idAprobacion, Long idResponsable) {
        //Esto me debe traer las informacion de la tabla de Aprobacion para que el
        //Responsable cambie el estado de la solicitud
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idResponsable);

        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idAprobacion);
        aprobacionDto.setEstadoByResponsable(EstadoByResponsableEnum.APROBADA.getNombre());
        aprobacionDto.setFkResponsable(usuarioDto);

        //Guardamos la solicitud con es estado cambiado
        AprobacionDto newAprobacionToOpe = aprobacionAbstract.guardarAprobacionAbstract(aprobacionDto);
        OperacionDto operacionDto = OperacionDto.builder()
                .id(null)
                .estadoByOperador(EstadoByOperadorEnum.PENDIENTE.getNombre())
                .fkSolicitud(newAprobacionToOpe.getFkSolicitud())
                .fkOperador(null)
                .build();

        //Guardamos la solicitud si el estado cambia a aprobado, en la tabla Operacion
        operacionAbstract.guardarOperacion(operacionDto);
    }

    @Override
    public void rechazarSolicitudService(Long idAprobacion, Long idResponsable) {
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idResponsable);

        AprobacionDto aprobacionDto = aprobacionAbstract.findAprovacionByIdSoliAbstract(idAprobacion);
        aprobacionDto.setEstadoByResponsable(EstadoByResponsableEnum.RECHAZADA.getNombre());
        aprobacionDto.setFkResponsable(usuarioDto);

        aprobacionAbstract.guardarAprobacionAbstract(aprobacionDto);
    }
}
