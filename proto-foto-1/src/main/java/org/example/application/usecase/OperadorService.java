package org.example.application.usecase;

import org.example.domain.enums.EstadoSolicitud;
import org.example.domain.model.Solicitud;

import java.util.List;

public interface OperadorService {
    List<Solicitud> verSolicitudes();
    boolean cambiarEstadoDeSolicitud(EstadoSolicitud nuevoEstado);
}
