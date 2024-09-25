package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.OperadorService;
import com.prototipo.domain.model.SolicitudDomain;

import java.util.List;

public class OperadorAdapter implements OperadorService {

    //Por que usamos SolicitudAbstract?? porque estamos Adaptando
    //la implementacion existente reutilizando su codigo
    //TODO se supone que unicamente se deberia hacer la consulta a la tabla de aprobaciones
    private SolicitudAbstract solicitudAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public OperadorAdapter(SolicitudAbstract solicitudAbstract,
                           MapperApplicationAbstract mapperApplicationAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public List<SolicitudDomain> verSolicitudes() {
        return solicitudAbstract.getListaSolicitudesAbstract()
                .stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, SolicitudDomain.class))
                .toList();
    }

    @Override
    public void cambiarEstadoDeSolicitud(Long idSolicitud) {
        SolicitudDto solicitudDto = solicitudAbstract.buscarSolicitudAbstract(idSolicitud);
        //TODO Aqui debe ir toda la logica
        //solicitudDto.setEstadoByOperador(EstadoByOperadorEnum.COMPLETADA.getNombre());
        solicitudAbstract.guardarSolicitudAbstract(solicitudDto);
    }
}
