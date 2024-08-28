package com.prototipo.application.port;

import com.prototipo.domain.enums.EstadoSolicitud;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public interface OperadorService {
    List<Solicitud> verSolicitudes();
    boolean cambiarEstadoDeSolicitud(Solicitud solicitud, EstadoSolicitud nuevoEstado);
}
