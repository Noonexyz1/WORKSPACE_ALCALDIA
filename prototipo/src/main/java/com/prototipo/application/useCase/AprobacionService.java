package com.prototipo.application.useCase;

import com.prototipo.domain.model.Aprobacion;

import java.util.List;

public interface AprobacionService {
    Aprobacion findAprovacionByIdSoliService(Long idAprobacion);
    List<Aprobacion> listaDeSolicitudesPendientesService(Long idSupervisor, Long page, Long size, String byColumName);
    List<Aprobacion> listaDeSolicitudesAprobadasService(Long idSupervisor, Long page, Long size, String byColumName);
    List<Aprobacion> listaDeSolicitudesRechazadasService(Long idSupervisor, Long page, Long size, String byColumName);
}
