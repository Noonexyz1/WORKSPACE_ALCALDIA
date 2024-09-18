package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.SolicitudAbstract;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolicitudImpl implements SolicitudAbstract {

    @Override
    public void solicitarFotocopiarAbstract(SolicitudDto solicitudDto) {

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
