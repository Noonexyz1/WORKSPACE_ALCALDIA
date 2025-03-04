package com.prototipo.application.port.out;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.Solicitud;

public interface SolicitudAbstract {
    Solicitud solicitarFotocopiarAbstract(Solicitud solicitud);
    Solicitud buscarSolicitudByIdAbstract(Long id);
    void guardarSolicitudAbstract(Solicitud solicitud);
    PaginableOut<Solicitud> getListaSolicitudesAbstract(PaginableIn paginableIn);
    PaginableOut<Solicitud> getListaSolicitudesAutoriAbstract(PaginableIn paginableIn);
    PaginableOut<Solicitud> getListaSolicitudesFinaliAbstract(PaginableIn paginableIn);
    PaginableOut<Solicitud> listaDeSolicitudesPendientesAbstractPageByIdSoli(PaginableIn paginableIn);
    PaginableOut<Solicitud> listaDeSolicitudesPendientesByIdResponsable(PaginableIn paginableIn);
}
