package com.prototipo.application.useCase;

import com.prototipo.domain.model.OperacionDomain;

import java.util.List;

public interface OperadorService {
    List<OperacionDomain> verSolicitudesDeOperador();
    void iniciarSolicitarOperacion(Long idSolicitud, Long idOperador);
    void terminarSolicitarOperacion(Long idSolicitud, Long idOperador);
}
