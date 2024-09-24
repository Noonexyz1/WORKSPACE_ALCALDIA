package com.prototipo.application.useCase;

import com.prototipo.domain.model.SolicitudDomain;

import java.util.List;

public interface OperadorService {
    List<SolicitudDomain> verSolicitudes();
    void cambiarEstadoDeSolicitud(Long idSolicitud);
}
