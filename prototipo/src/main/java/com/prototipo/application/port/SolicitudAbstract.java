package com.prototipo.application.port;

import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

import java.util.List;

public interface SolicitudAbstract {
    SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto);
    SolicitudDto buscarSolicitudByIdAbstract(Long id);
    void guardarSolicitudAbstract(SolicitudDto solicitudDto);
    PaginableOut<SolicitudDto> getListaSolicitudesAbstract(PaginableIn paginableIn);
    PaginableOut<SolicitudDto> getListaSolicitudesAutoriAbstract(PaginableIn paginableIn);
    PaginableOut<SolicitudDto> getListaSolicitudesFinaliAbstract(PaginableIn paginableIn);




    //TODO, estos metodos no tienen relacion con esta interfaz
    List<FotocopiaDto> getFotocopiasSolicitudAbstract(Long idSolicitud);
    void guardarAutorizacionAbs(AutorizacionDto autorizacionDto);
    AutorizacionDto buscarAutorizacionByIdAbs(Long idAutorizacion);
    void guardarFinalizacionAbs(FinalizacionDto finalizacionDto);
    void guardarRegistroFotocopia(FotocopiaDto fotocopiaDto);
    ServicioFotocopiaDto findServicioFotocopia(ServicioFotocopiaDto fkServicioFotocopia);
}
