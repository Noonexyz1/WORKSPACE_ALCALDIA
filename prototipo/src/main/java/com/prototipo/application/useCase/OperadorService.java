package com.prototipo.application.useCase;

import com.prototipo.domain.model.OperacionDomain;

import java.util.List;

public interface OperadorService {
    List<OperacionDomain> verSolicitudesDeOperador(Long idOperador, String estadoOperador, Long page, Long size);
    void iniciarSolicitudOperacion(Long idOperacion, Long idOperador);
    void terminarSolicitudOperacion(Long idOperacion, Long idOperador);
}
