package com.prototipo.application.useCase;

import com.prototipo.domain.model.*;

import java.util.List;

public interface SolicitudService {
    void solicitarFotocopiarService(Solicitud solicitudDomain, List<Fotocopia> list);
    List<Solicitud> getListaSolicitudesService(Long idUsuarioUnidad, Long page, Long size);
    Solicitud buscarSolicitudService(Long id);
    List<Fotocopia> listFotocopiaSolicitud(Long idSolicitud);
    List<Fotocopia> findListDetalleSoliBySolicitudId(Long idSolicitud);
    void guardarAutorizacion(Autorizacion autorizacion);
    void guardarFinalizacion(Finalizacion finalizacion);
    void eliminarSolicitudById(Long idSolicitud);
    List<String> listarTamano();
    List<String> listarAnversoReverso();
    List<String> listarColor();
    List<Solicitud> getListaSolicitudesAutoriService(Long idUserUni, Long page, Long size);
    List<Solicitud> getListaSolicitudesFinaliService(Long idUserUni, Long page, Long size);
}
