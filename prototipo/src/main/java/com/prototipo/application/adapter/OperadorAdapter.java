package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.modelDto.OperadorUnidadDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.port.OperadorUnidadAbstract;
import com.prototipo.application.port.SolicitudAbstract;
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
    public List<OperacionDomain> verSolicitudesDeOperador(Long idOperador, String estadoOperador) {
        //Si algun registro objeto es null en la base de datos, aunque unicamente sea uno,
        //pues al hacer x.getFkOperador(), to-do el stream falla
        return funcionToReturn(idOperador, estadoOperador);
    }

    private List<OperacionDomain> funcionToReturn(Long idOperador, String estadoOpe){
        /*//Tengo que enviar el id del usuario anterior, para luego buscar por fkUsuarioId en
        //tabla OperadorUnidad, tengo que encontrar por FK
        //List<OperadorUnidadDto> listOperadorUnidadDto = operadorUnidadAbstract.encontrarOperadorUnidadById(idOperador);

        //TODO, ahora traemos los fk de de las unidades de listOperadorUnidadDto
        //List<Long> listFkUnidades = listOperadorUnidadDto.stream()
        //        .map(x -> x.getFkUnidad().getId()).toList();
        //Funciona, me esta traendo todos los id de unidad Correspondientes al operador que los sirve
        //entonces necesito buscar todas aquellas coincidencias que existen en Solicitudes con list listFkUnidades


        //TODO, luego busco directamente en la tabla Solicitud por campo fkUnidad, traigo sus Ids de esta
        //List<SolicitudDto> listSolicitudes = listFkUnidades.stream()
        //        .map(x -> solicitudAbstract.buscarSolicitudByFkUnidad(x))
        //        .toList();

        //TODO, con esos Ids busco en el Aprobaciones por fkSolicitud, luego saco los ID de aprobacion
        //TODO, y busco en en Operacion por fkAprobaciones.


        List<OperacionDto> operacionDtoList = operacionAbstract.listaDeOperaciones()
                .stream()
                .filter(x -> x.getFkOperador() != null && x.getFkOperador().getId() == idOperador)
                .filter(x -> x.getEstadoByOperador().equals(estadoOpe))
                .toList();*/

        List<OperacionDto> listOpe = operacionAbstract.findOperacionByIdOperadorAbstract(idOperador, estadoOpe);

        return  listOpe.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, OperacionDomain.class))
                .toList();
    }

    //TODO, revisar este metodo
    @Override
    public void iniciarSolicitarOperacion(Long idAprobacion, Long idOperador) {
        //Aqui debe ir toda la logica
        //Modificamos esta instancia para poder cambiar el estado de esta Operacion a iniciado
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idAprobacion);
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
    public void terminarSolicitarOperacion(Long idAprobacion, Long idOperador) {
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idAprobacion);
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);

        operacionDto.setId(null);
        operacionDto.setEstadoByOperador(EstadoByOperadorEnum.COMPLETADO.getNombre());
        operacionDto.setFkSolicitud(operacionDto.getFkSolicitud());
        operacionDto.setFkOperador(usuarioDto);
        operacionAbstract.guardarOperacion(operacionDto);
    }
}
