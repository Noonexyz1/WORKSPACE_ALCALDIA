package com.prototipo.application.useCase;

import com.prototipo.domain.model.OperacionDomain;

import java.util.List;

public interface OperadorService {
    List<OperacionDomain> verSolicitudesDeOperador(Long idOperador);
    List<OperacionDomain> verSolicitudesDeOperadorIniciadas(Long idOperador);
    List<OperacionDomain> verSolicitudesDeOperadorCompletadas(Long idOperador);
    void iniciarSolicitarOperacion(Long idAprobacion, Long idOperador);
    void terminarSolicitarOperacion(Long idAprobacion, Long idOperador);
}
