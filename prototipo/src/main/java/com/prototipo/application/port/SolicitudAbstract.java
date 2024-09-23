package com.prototipo.application.port;

import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.modelDto.SolicitudDto;

import java.util.List;

public interface SolicitudAbstract {
    SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto);
    void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDto archivoPdfDto);
    List<SolicitudDto> getListaSolicitudesAbstract();
}
