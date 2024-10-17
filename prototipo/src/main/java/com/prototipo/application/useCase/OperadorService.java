package com.prototipo.application.useCase;

import com.prototipo.domain.model.ArchivoPdf;
import com.prototipo.domain.model.Operacion;

import java.util.List;

public interface OperadorService {
    List<Operacion> verSolicitudesDeOperadorPendientes(Long idOperador, Long page, Long size);
    List<Operacion> verSolicitudesDeOperadorIniciadas(Long idOperador, Long page, Long size);
    List<Operacion> verSolicitudesDeOperadorCompletadas(Long idOperador, Long page, Long size);
    List<ArchivoPdf> iniciarSolicitudOperacion(Long idOperacion, Long idOperador);
    void terminarSolicitudOperacion(Long idOperacion);
}
