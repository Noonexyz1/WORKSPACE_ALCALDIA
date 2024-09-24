package com.prototipo.application.useCase;

public interface ResponsableService {
    void aprobarSolicitudService(Long idSolicitud, Long idResponsable);
    void rechazarSolicitudService(Long idSolicitud, Long idResponsable);
}
