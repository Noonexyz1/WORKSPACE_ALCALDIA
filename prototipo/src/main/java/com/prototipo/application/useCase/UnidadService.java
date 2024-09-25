package com.prototipo.application.useCase;

import com.prototipo.domain.model.UnidadDomain;

import java.util.List;

public interface UnidadService {
    List<UnidadDomain> listaDeUnidadesService();
    UnidadDomain findUnidadPorIdService(Long idUnidad);
}
