package com.prototipo.application.useCase;

import com.prototipo.domain.model.Autorizacion;
import com.prototipo.domain.model.Finalizacion;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public interface AprobacionService {
    List<Solicitud> listaDeSolicitudesPendientesService(Long idSupervisor, Long page, Long size, String byColumName);
    List<Finalizacion> listaDeSolicitudesFinalizadasService(Long idSupervisor, Long page, Long size, String byColumName);
    List<Autorizacion> listaDeSolicitudesAutorizadasService(Long idSupervisor, Long page, Long size, String byColumName);
    Autorizacion findAutorizacionByIdSoliService(Long idSolicitud);
}
