package com.prototipo.application.useCase;

import com.prototipo.domain.model.ArchivoPdf;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public interface SolicitudService {
    void solicitarFotocopiarService(Solicitud solicitudDomain, List<ArchivoPdf> list);
    void guardarPdfDeLaSolicitudAbstract(ArchivoPdf archivoPdfDomain);
    List<Solicitud> getListaSolicitudesService(Long idUsuario, Long page, Long size);
    void guardarSolicitudService(Solicitud solicitudDomain);
    Solicitud buscarSolicitudService(Long id);
}
