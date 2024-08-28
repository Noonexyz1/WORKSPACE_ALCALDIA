package com.prototipo.application.port;

import com.prototipo.domain.model.Solicitud;

import java.util.List;

public interface SolicitudPersist {
    void solicitarFotocopiar(Solicitud solicitud);

    List<Solicitud> getListaSolicitudes();

    List<Solicitud> getSolicitudesOperador();
}
