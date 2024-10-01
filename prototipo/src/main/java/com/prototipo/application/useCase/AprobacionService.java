package com.prototipo.application.useCase;

import com.prototipo.domain.model.AprobacionDomain;

import java.util.List;

public interface AprobacionService {
    AprobacionDomain findAprovacionByIdSoliService(Long idAprobacion);
    List<AprobacionDomain> listaDeSolicitudesService(Long idSupervisor);
}
