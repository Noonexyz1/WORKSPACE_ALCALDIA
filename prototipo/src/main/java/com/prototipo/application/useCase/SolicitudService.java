package com.prototipo.application.useCase;

import com.prototipo.domain.model.Solicitud;

import java.util.List;

public interface SolicitudService {
    void solicitarFotocopiarService(Solicitud solicitud);
    List<Solicitud> getListaSolicitudesService();
    List<Solicitud> getSolicitudesOperadorService();
}
