package com.prototipo.application.useCase;

import com.prototipo.domain.model.*;

import java.util.List;

public interface SolicitudService {
    void solicitarFotocopiarService(Solicitud solicitudDomain, List<DetalleSolicitud> list);
    void registrarFotocopiarService(Solicitud solicitudDomain, List<DetalleSolicitud> list);
    void guardarPdfDeLaSolicitudAbstract(ArchivoPdf archivoPdfDomain);
    void guardarSolicitudService(Solicitud solicitudDomain);

    List<Solicitud> getListaSolicitudesService(Long idUsuarioUnidad, Long page, Long size);
    Solicitud buscarSolicitudService(Long id);
    List<DetalleSolicitud> listDetalleSolicitud(Long idSolicitud);
    List<Finalizacion> listFinalizacionSolicitud(Long idFunUni, Long page, Long size);

    List<DetalleSolicitud> findListDetalleSoliBySolicitudId(Long idSolicitud);

    void guardarAutorizacion(Autorizacion autorizacion);

    Autorizacion buscarAutorizacionByIdSoli(Long idSolicitud);

    void guardarFinalizacion(Finalizacion finalizacion);
}
