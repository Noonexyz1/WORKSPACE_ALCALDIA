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
import com.prototipo.domain.model.ArchivoPdf;
import com.prototipo.domain.model.Operacion;

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
    public List<Operacion> verSolicitudesDeOperadorPendientes(Long idOperador, Long page, Long size) {
        List<OperacionDto> listOpe = operacionAbstract
                .findOperacionByIdOperadorPendientesAbstract(idOperador, page, size);
        return  listOpe.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Operacion.class))
                .toList();
    }

    @Override
    public List<Operacion> verSolicitudesDeOperadorIniciadas(Long idOperador, Long page, Long size) {
        List<OperacionDto> listOpe = operacionAbstract
                .findOperacionByIdOperadorIniciadasAbstract(idOperador, page, size);
        return  listOpe.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Operacion.class))
                .toList();
    }

    @Override
    public List<Operacion> verSolicitudesDeOperadorCompletadas(Long idOperador, Long page, Long size) {
        List<OperacionDto> listOpe = operacionAbstract
                .findOperacionByIdOperadorCompletadasAbstract(idOperador, page, size);
        return  listOpe.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Operacion.class))
                .toList();
    }

    @Override
    public List<ArchivoPdf> iniciarSolicitudOperacion(Long idOperacion, Long idOperador) {
        //Aqui debe ir toda la logica
        //Modificamos esta instancia para poder cambiar el estado de esta Operacion a iniciado
        UsuarioDto usuarioOperador = UsuarioDto.builder().id(idOperador).build();

        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idOperacion);
        operacionDto.setEstadoCambio(1);
        operacionDto = operacionAbstract.guardarOperacion(operacionDto);
        operacionDto.setId(null);
        operacionDto.setEstadoByOperador(EstadoByOperadorEnum.INICIADO.getNombre());
        operacionDto.setFkOperador(usuarioOperador);

        OperacionDto operacionDtoResp = operacionAbstract.guardarOperacion(operacionDto);

        //Para descargar los archivos PDFs que son de un ID
        Long idSolicitud = operacionDtoResp.getFkSolicitud().getId();
        List<ArchivoPdfDto> listArchResp = archivoPdfAbstract.listaDePdfsById(idSolicitud);

        return listArchResp.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, ArchivoPdf.class))
                .toList();
    }

    @Override
    public void terminarSolicitudOperacion(Long idOperacion) {
        OperacionDto operacionDto = operacionAbstract.findOperacionByIdSoliAbstract(idOperacion);
        operacionDto.setEstadoCambio(2);
        operacionDto = operacionAbstract.guardarOperacion(operacionDto);
        operacionDto.setId(null);
        operacionDto.setEstadoByOperador(EstadoByOperadorEnum.COMPLETADO.getNombre());

        operacionAbstract.guardarOperacion(operacionDto);
    }
}
