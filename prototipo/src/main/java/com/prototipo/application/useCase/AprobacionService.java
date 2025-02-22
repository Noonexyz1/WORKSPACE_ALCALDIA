package com.prototipo.application.useCase;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.Autorizacion;
import com.prototipo.domain.model.Finalizacion;
import com.prototipo.domain.model.Solicitud;

public interface AprobacionService {
    PaginableOut<Solicitud> listaDeSolicitudesPendientesService(PaginableIn paginableIn);
    PaginableOut<Finalizacion> listaDeSolicitudesFinalizadasService(PaginableIn paginableIn);
    PaginableOut<Autorizacion> listaDeSolicitudesAutorizadasService(PaginableIn paginableIn);
    Autorizacion findAutorizacionByIdSoliService(Long idSolicitud);
}
