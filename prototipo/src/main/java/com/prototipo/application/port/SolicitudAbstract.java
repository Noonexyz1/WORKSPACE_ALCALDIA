package com.prototipo.application.port;

import com.prototipo.application.modelDto.*;

import java.util.List;

public interface SolicitudAbstract {
    SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto);
    List<SolicitudDto> getListaSolicitudesAbstract(Long idUsuario, Long page, Long size);
    void guardarSolicitudAbstract(SolicitudDto solicitudDto);
    SolicitudDto buscarSolicitudByIdAbstract(Long id);
    List<FotocopiaDto> getFotocopiasSolicitudAbstract(Long idSolicitud);
    List<FotocopiaDto> findListDetalleSoliBySolicitudIdAbs(Long idSolicitud);
    void guardarAutorizacionAbs(AutorizacionDto autorizacionDto);
    AutorizacionDto buscarAutorizacionByIdAbs(Long idAutorizacion);
    void guardarFinalizacionAbs(FinalizacionDto finalizacionDto);
    void guardarRegistroFotocopia(FotocopiaDto fotocopiaDto);
    ServicioFotocopiaDto findServicioFotocopia(ServicioFotocopiaDto fkServicioFotocopia);
    List<SolicitudDto> getListaSolicitudesAutoriAbstract(Long idUserUni, Long page, Long size);
    List<SolicitudDto> getListaSolicitudesFinaliAbstract(Long idUserUni, Long page, Long size);
}
