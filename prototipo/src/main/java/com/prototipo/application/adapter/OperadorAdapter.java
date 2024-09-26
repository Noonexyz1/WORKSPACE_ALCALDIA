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
    public List<OperacionDomain> verSolicitudesDeOperador(Long idOperador) {
        //Si algun registro objeto es null en la base de datos, aunque unicamente sea uno,
        //pues al hacer x.getFkOperador(), toDo el stream falla
        return funcionToReturn(idOperador, EstadoByOperadorEnum.PENDIENTE.getNombre());
    }

    @Override
    public List<OperacionDomain> verSolicitudesDeOperadorIniciadas(Long idOperador) {
        return funcionToReturn(idOperador, EstadoByOperadorEnum.INICIADO.getNombre());
    }

    @Override
    public List<OperacionDomain> verSolicitudesDeOperadorCompletadas(Long idOperador) {
        return funcionToReturn(idOperador, EstadoByOperadorEnum.COMPLETADO.getNombre());
    }

    @Override
    public void iniciarSolicitarOperacion(Long idAprobacion, Long idOperador) {
        //Aqui debe ir toda la logica
        //Modificamos esta instancia para poder cambiar el estado de esta Operacion a iniciado
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idAprobacion);
        UsuarioDto usuarioOperador = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);

        OperacionDto operadorDtoSave = OperacionDto.builder()
                .id(null)
                .estadoByOperador(EstadoByOperadorEnum.INICIADO.getNombre())
                .fkAprobacion(operacionDto.getFkAprobacion())
                .fkOperador(usuarioOperador)
                .build();

        operacionAbstract.guardarOperacion(operadorDtoSave);
        //si operador es null de Operacion, entonces le actualizamos a usuarioOperador que significa
        //que esta operacion ya ha sido inicado por el operador y que ese inicio ha sido registrado
        //como nuevo registro
        operacionDto.setFkOperador(usuarioOperador);
        operacionAbstract.guardarOperacion(operacionDto);
    }

    @Override
    public void terminarSolicitarOperacion(Long idAprobacion, Long idOperador) {
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idAprobacion);
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);

        operacionDto.setId(null);
        operacionDto.setEstadoByOperador(EstadoByOperadorEnum.COMPLETADO.getNombre());
        operacionDto.setFkOperador(usuarioDto);
        operacionAbstract.guardarOperacion(operacionDto);
    }

    private List<OperacionDomain> funcionToReturn(Long idOperador, String opcion){
        List<OperacionDto> operacionDtoList = operacionAbstract.listaDeOperaciones()
                .stream()
                .filter(x -> x.getFkOperador() != null && x.getFkOperador().getId() == idOperador)
                .filter(x -> x.getEstadoByOperador().equals(opcion))
                .toList();

        return operacionDtoList.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, OperacionDomain.class))
                .toList();
    }
}
