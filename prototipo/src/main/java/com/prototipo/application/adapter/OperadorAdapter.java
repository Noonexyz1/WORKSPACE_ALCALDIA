package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.OperadorService;
import com.prototipo.domain.enums.EstadoByOperadorEnum;
import com.prototipo.domain.model.OperacionDomain;

import java.util.List;

public class OperadorAdapter implements OperadorService {

    //Por que usamos SolicitudAbstract?? porque estamos Adaptando
    //la implementacion existente reutilizando su codigo
    private MapperApplicationAbstract mapperApplicationAbstract;
    private OperacionAbstract operacionAbstract;
    private UsuarioAbastract usuarioAbastract;

    public OperadorAdapter(MapperApplicationAbstract mapperApplicationAbstract,
                           OperacionAbstract operacionAbstract,
                           UsuarioAbastract usuarioAbastract) {

        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.operacionAbstract = operacionAbstract;
        this.usuarioAbastract = usuarioAbastract;
    }

    @Override
    public List<OperacionDomain> verSolicitudesDeOperador(Long idOperador, String estadoOperador, Long page, Long size) {
        List<OperacionDto> listOpe = operacionAbstract.findOperacionByIdOperadorAbstract(idOperador, estadoOperador, page, size);
        return  listOpe.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, OperacionDomain.class))
                .toList();
    }

    @Override
    public void iniciarSolicitudOperacion(Long idOperacion, Long idOperador) {
        //Aqui debe ir toda la logica
        //Modificamos esta instancia para poder cambiar el estado de esta Operacion a iniciado
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idOperacion);
        UsuarioDto usuarioOperador = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);

        OperacionDto operadorDtoSave = OperacionDto.builder()
                .id(null)
                .estadoByOperador(EstadoByOperadorEnum.INICIADO.getNombre())
                .fkSolicitud(operacionDto.getFkSolicitud())
                .fkOperador(usuarioOperador)
                .build();

        operacionAbstract.guardarOperacion(operadorDtoSave);
        //si operador es null de Operacion, entonces le actualizamos a usuarioOperador que significa
        //que esta operacion ya ha sido inicado por el operador y que ese inicio ha sido registrado
        //como nuevo registro
        //operacionDto.setFkOperador(usuarioOperador);
        //operacionAbstract.guardarOperacion(operacionDto);
    }

    @Override
    public void terminarSolicitudOperacion(Long idOperacion, Long idOperador) {
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idOperacion);
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);

        OperacionDto operadorDtoSave = OperacionDto.builder()
                .id(null)
                .estadoByOperador(EstadoByOperadorEnum.COMPLETADO.getNombre())
                .fkSolicitud(operacionDto.getFkSolicitud())
                .fkOperador(usuarioDto)
                .build();

        operacionAbstract.guardarOperacion(operadorDtoSave);
    }
}
