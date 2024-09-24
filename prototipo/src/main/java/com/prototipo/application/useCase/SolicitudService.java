package com.prototipo.application.useCase;

import com.prototipo.domain.model.ArchivoPdfDomain;
import com.prototipo.domain.model.SolicitudDomain;

import java.util.List;

public interface SolicitudService {
    void solicitarFotocopiarService(SolicitudDomain solicitudDomain, List<ArchivoPdfDomain> list);
    void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDomain archivoPdfDomain);
    List<SolicitudDomain> getListaSolicitudesService();
    void guardarSolicitudService(SolicitudDomain solicitudDomain);
    SolicitudDomain buscarSolicitudService(Long id);
}
