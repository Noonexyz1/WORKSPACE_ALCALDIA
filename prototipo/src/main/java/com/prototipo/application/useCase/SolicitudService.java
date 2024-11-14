package com.prototipo.application.useCase;

import com.prototipo.domain.model.ArchivoPdf;
import com.prototipo.domain.model.DetalleSolicitud;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public interface SolicitudService {
    void solicitarFotocopiarService(Solicitud solicitudDomain, List<ArchivoPdf> list);
    void registrarFotocopiarService(Solicitud solicitudDomain, List<DetalleSolicitud> list);
    void guardarPdfDeLaSolicitudAbstract(ArchivoPdf archivoPdfDomain);
    void guardarSolicitudService(Solicitud solicitudDomain);
    List<Solicitud> getListaSolicitudesService(Long idUsuario, Long page, Long size);
    Solicitud buscarSolicitudService(Long id);
    List<DetalleSolicitud> listDetalleSolicitud(Long idSolicitud);
}
