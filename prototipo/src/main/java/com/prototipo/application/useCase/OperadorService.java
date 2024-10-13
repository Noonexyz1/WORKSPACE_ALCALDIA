package com.prototipo.application.useCase;

import com.prototipo.domain.model.ArchivoPdfDomain;
import com.prototipo.domain.model.OperacionDomain;

import java.util.List;

public interface OperadorService {
    List<OperacionDomain> verSolicitudesDeOperadorPendientes(Long idOperador, Long page, Long size);
    List<OperacionDomain> verSolicitudesDeOperadorIniciadas(Long idOperador, Long page, Long size);
    List<OperacionDomain> verSolicitudesDeOperadorCompletadas(Long idOperador, Long page, Long size);
    List<ArchivoPdfDomain> iniciarSolicitudOperacion(Long idOperacion, Long idOperador);
    void terminarSolicitudOperacion(Long idOperacion, Long idOperador);
}
