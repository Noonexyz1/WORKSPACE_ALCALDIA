package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.OperadorService;
import com.prototipo.domain.enums.EstadoByOperadorEnum;
import com.prototipo.domain.model.OperacionDomain;

import java.util.List;

public class OperadorAdapter implements OperadorService {

    //Por que usamos SolicitudAbstract?? porque estamos Adaptando
    //la implementacion existente reutilizando su codigo
    //TODO se supone que unicamente se deberia hacer la consulta a la tabla de aprobaciones
    private SolicitudAbstract solicitudAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private OperacionAbstract operacionAbstract;
    private UsuarioAbastract usuarioAbastract;


    public OperadorAdapter(SolicitudAbstract solicitudAbstract,
                           MapperApplicationAbstract mapperApplicationAbstract,
                           OperacionAbstract operacionAbstract,
                           UsuarioAbastract usuarioAbastract) {

        this.solicitudAbstract = solicitudAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.operacionAbstract = operacionAbstract;
        this.usuarioAbastract = usuarioAbastract;
    }

    @Override
    public List<OperacionDomain> verSolicitudesDeOperador() {
        return operacionAbstract.listaDeOperaciones()
                .stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, OperacionDomain.class))
                .toList();
    }

    @Override
    public void iniciarSolicitarOperacion(Long idSolicitud, Long idOperador) {
        //Aqui debe ir toda la logica
        //Modificamos esta instancia para poder cambiar el estado de esta Operacion a iniciado
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idSolicitud);
        UsuarioDto usuarioOperador = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);

        operacionDto.setId(null);
        operacionDto.setEstadoByOperador(EstadoByOperadorEnum.INICIADO.getNombre());
        operacionDto.setFkOperador(usuarioOperador);
        operacionAbstract.guardarOperacion(operacionDto);
    }

    @Override
    public void terminarSolicitarOperacion(Long idSolicitud, Long idOperador) {
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idSolicitud);
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);

        operacionDto.setId(null);
        operacionDto.setEstadoByOperador(EstadoByOperadorEnum.COMPLETADO.getNombre());
        operacionDto.setFkOperador(usuarioDto);
        operacionAbstract.guardarOperacion(operacionDto);
    }
}
