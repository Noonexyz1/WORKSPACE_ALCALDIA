package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.ArchivoPdfAbstract;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.OperadorService;
import com.prototipo.domain.enums.EstadoByOperadorEnum;
import com.prototipo.domain.model.ArchivoPdfDomain;
import com.prototipo.domain.model.OperacionDomain;

import java.util.List;

public class OperadorAdapter implements OperadorService {

    //Por que usamos SolicitudAbstract?? porque estamos Adaptando
    //la implementacion existente reutilizando su codigo
    private MapperApplicationAbstract mapperApplicationAbstract;
    private OperacionAbstract operacionAbstract;
    private UsuarioAbastract usuarioAbastract;
    private ArchivoPdfAbstract archivoPdfAbstract;

    public OperadorAdapter(MapperApplicationAbstract mapperApplicationAbstract,
                           OperacionAbstract operacionAbstract,
                           UsuarioAbastract usuarioAbastract,
                           ArchivoPdfAbstract archivoPdfAbstract) {

        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.operacionAbstract = operacionAbstract;
        this.usuarioAbastract = usuarioAbastract;
        this.archivoPdfAbstract = archivoPdfAbstract;
    }

    @Override
    public List<OperacionDomain> verSolicitudesDeOperadorPendientes(Long idOperador, Long page, Long size) {
        List<OperacionDto> listOpe = operacionAbstract.findOperacionByIdOperadorPendientesAbstract(idOperador, page, size);
        return  listOpe.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, OperacionDomain.class))
                .toList();
    }

    @Override
    public List<OperacionDomain> verSolicitudesDeOperadorIniciadas(Long idOperador, Long page, Long size) {
        List<OperacionDto> listOpe = operacionAbstract.findOperacionByIdOperadorIniciadasAbstract(idOperador, page, size);
        return  listOpe.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, OperacionDomain.class))
                .toList();
    }

    @Override
    public List<OperacionDomain> verSolicitudesDeOperadorCompletadas(Long idOperador, Long page, Long size) {
        List<OperacionDto> listOpe = operacionAbstract.findOperacionByIdOperadorCompletadasAbstract(idOperador, page, size);
        return  listOpe.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, OperacionDomain.class))
                .toList();
    }

    @Override
    public List<ArchivoPdfDomain> iniciarSolicitudOperacion(Long idOperacion, Long idOperador) {
        //Aqui debe ir toda la logica
        //Modificamos esta instancia para poder cambiar el estado de esta Operacion a iniciado
        UsuarioDto usuarioOperador = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idOperacion);
        operacionDto.setEstadoByOperador(EstadoByOperadorEnum.INICIADO.getNombre());
        operacionDto.setFkOperador(usuarioOperador);

        OperacionDto operacionDtoResp = operacionAbstract.guardarOperacion(operacionDto);
        Long idSolicitud = operacionDtoResp.getFkSolicitud().getId();
        List<ArchivoPdfDto> listArchResp = archivoPdfAbstract.listaDePdfsById(idSolicitud);

        //si operador es null de Operacion, entonces le actualizamos a usuarioOperador que significa
        //que esta operacion ya ha sido inicado por el operador y que ese inicio ha sido registrado
        //como nuevo registro
        //operacionDto.setFkOperador(usuarioOperador);
        //operacionAbstract.guardarOperacion(operacionDto);

        return listArchResp.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, ArchivoPdfDomain.class))
                .toList();
    }

    @Override
    public void terminarSolicitudOperacion(Long idOperacion, Long idOperador) {
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idOperador);
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idOperacion);
        operacionDto.setId(null);
        operacionDto.setEstadoByOperador(EstadoByOperadorEnum.COMPLETADO.getNombre());

        operacionAbstract.guardarOperacion(operacionDto);
    }
}
