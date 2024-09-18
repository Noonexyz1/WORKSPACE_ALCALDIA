package com.prototipo.application.port;

import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public interface SolicitudAbstract {
    void solicitarFotocopiarAbstract(SolicitudDto solicitudDto);
    List<SolicitudDto> getListaSolicitudesAbstract();
    List<SolicitudDto> getSolicitudesOperadorAbstract();
}
