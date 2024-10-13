package com.prototipo.application.useCase;

import com.prototipo.domain.model.AprobacionDomain;

import java.util.List;

public interface AprobacionService {
    AprobacionDomain findAprovacionByIdSoliService(Long idAprobacion);
    List<AprobacionDomain> listaDeSolicitudesPendientesService(Long idSupervisor, Long page, Long size, String byColumName);
    List<AprobacionDomain> listaDeSolicitudesAprobadasService(Long idSupervisor, Long page, Long size, String byColumName);
    List<AprobacionDomain> listaDeSolicitudesRechazadasService(Long idSupervisor, Long page, Long size, String byColumName);
}
