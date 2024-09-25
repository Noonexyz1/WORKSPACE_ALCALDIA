package com.prototipo.application.useCase;

public interface ResponsableService {
    void aprobarSolicitudService(Long idAprobacion, Long idResponsable);
    void rechazarSolicitudService(Long idAprobacion, Long idResponsable);
}
