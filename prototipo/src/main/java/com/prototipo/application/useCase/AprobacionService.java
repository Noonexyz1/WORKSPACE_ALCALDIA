package com.prototipo.application.useCase;

import com.prototipo.domain.model.Aprobacion;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.infrastructure.rest.response.SolicitudResponResponse;

import java.util.List;

public interface AprobacionService {
    Aprobacion findAprovacionByIdSoliService(Long idAprobacion);
    List<Solicitud> listaDeSolicitudesPendientesService(Long idSupervisor, Long page, Long size, String byColumName);
    List<Aprobacion> listaDeSolicitudesAprobadasService(Long idSupervisor, Long page, Long size, String byColumName);
    List<Aprobacion> listaDeSolicitudesRechazadasService(Long idSupervisor, Long page, Long size, String byColumName);
}
