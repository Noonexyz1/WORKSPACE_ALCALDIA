package org.example.application.usecase;

import org.example.domain.model.Solicitud;

public interface CorreoServicio {
    void enviarCorreoDeEstado(Solicitud solicitud);
}
