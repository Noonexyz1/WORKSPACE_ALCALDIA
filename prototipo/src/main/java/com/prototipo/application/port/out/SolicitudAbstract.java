package com.prototipo.application.port.out;

import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

public interface SolicitudAbstract {
    SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto);
    SolicitudDto buscarSolicitudByIdAbstract(Long id);
    void guardarSolicitudAbstract(SolicitudDto solicitudDto);
    PaginableOut<SolicitudDto> getListaSolicitudesAbstract(PaginableIn paginableIn);
    PaginableOut<SolicitudDto> getListaSolicitudesAutoriAbstract(PaginableIn paginableIn);
    PaginableOut<SolicitudDto> getListaSolicitudesFinaliAbstract(PaginableIn paginableIn);
    PaginableOut<SolicitudDto> listaDeSolicitudesPendientesAbstractPageByIdSoli(PaginableIn paginableIn);
    PaginableOut<SolicitudDto> listaDeSolicitudesPendientesByIdResponsable(PaginableIn paginableIn);
}
