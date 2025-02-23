package com.prototipo.application.useCase;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.*;

import java.util.List;

public interface SolicitudService {
    void solicitarFotocopiarService(Solicitud solicitudDomain, List<Fotocopia> list);
    PaginableOut<Solicitud> getListaSolicitudesService(PaginableIn paginableIn);
    Solicitud buscarSolicitudService(Long id);
    List<Fotocopia> listFotocopiaSolicitud(Long idSolicitud);
    List<Fotocopia> findListDetalleSoliBySolicitudId(Long idSolicitud);
    void guardarAutorizacion(Autorizacion autorizacion);
    void guardarFinalizacion(Finalizacion finalizacion);
    void eliminarSolicitudById(Long idSolicitud);
    List<String> listarTamano();
    List<String> listarAnversoReverso();
    List<String> listarColor();
    PaginableOut<Solicitud> getListaSolicitudesAutoriService(PaginableIn paginableIn);
    PaginableOut<Solicitud> getListaSolicitudesFinaliService(PaginableIn paginableIn);
}
