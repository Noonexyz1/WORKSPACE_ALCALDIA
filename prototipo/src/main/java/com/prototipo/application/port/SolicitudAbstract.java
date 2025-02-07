package com.prototipo.application.port;

import com.prototipo.application.modelDto.*;

import java.util.List;

public interface SolicitudAbstract {
    SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto);
    void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDto archivoPdfDto);
    void guardarRegistroSolicitud(DetalleSolicitudDto detalleSolicitudDto);
    List<SolicitudDto> getListaSolicitudesAbstract(Long idUsuario, Long page, Long size);
    List<SolicitudDto> getListaSolicitudesByUnidad(Long idUnidad);
    void guardarSolicitudAbstract(SolicitudDto solicitudDto);
    SolicitudDto buscarSolicitudByIdAbstract(Long id);
    SolicitudDto buscarSolicitudByFkUnidad(Long idUnidad);
    List<DetalleSolicitudDto> getListaDetalleSolicitudAbstract(Long idSolicitud);
    List<FinalizacionDto> listFinalizacionSolicitudAbs(Long idFunUni, Long page, Long size);
    List<DetalleSolicitudDto> findListDetalleSoliBySolicitudIdAbs(Long idSolicitud);
    void guardarCotizacionAbs(CotizacionDto cotizacionDto);
    void guardarAutorizacionAbs(AutorizacionDto autorizacionDto);
    AutorizacionDto buscarAutorizacionByIdSoliAbs(Long idSolicitud);
    AutorizacionDto buscarAutorizacionByIdAbs(Long idAutorizacion);
    void guardarFinalizacionAbs(FinalizacionDto finalizacionDto);
}
