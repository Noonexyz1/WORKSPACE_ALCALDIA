package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Solicitud;
import com.prototipo.infrastructure.persistence.db.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolicitudImpl implements SolicitudAbstract {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Override
    public void solicitarFotocopiarAbstract(SolicitudDto solicitudDto) {
        //TODO -------------realizar las implementaciones

        Solicitud solicitud = Solicitud.builder()
                .nroDeCopias(solicitudDto.getNroDeCopias())
                .tipoDeDocumento(solicitudDto.getTipoDeDocumento())
                .nroDePaginas(solicitudDto.getNroDePaginas())
                //estoy hardcoeando por fines practicos
                .estadoSolicitud("Pendiente")
                .notificacionToAprobar("Pendiente")
                //TODO--- falta la prueba de los archivos
                .build();

        solicitudRepository.save(solicitud);

    }

    @Override
    public List<SolicitudDto> getListaSolicitudesAbstract() {
        return List.of();
    }

    @Override
    public List<SolicitudDto> getSolicitudesOperadorAbstract() {
        return List.of();
    }
}
