package com.prototipo.application.port;

import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.modelDto.SolicitudDto;

import java.util.List;

public interface SolicitudAbstract {
    SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto);
    void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDto archivoPdfDto);
    List<SolicitudDto> getListaSolicitudesAbstract(Long idUsuario);
    List<SolicitudDto> getListaSolicitudesByUnidad(Long idUnidad);
    void guardarSolicitudAbstract(SolicitudDto solicitudDto);
    SolicitudDto buscarSolicitudAbstract(Long id);
    SolicitudDto buscarSolicitudByFkUnidad(Long idUnidad);
}
