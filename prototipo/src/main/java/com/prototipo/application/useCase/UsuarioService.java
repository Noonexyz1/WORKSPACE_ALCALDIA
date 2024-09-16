package com.prototipo.application.useCase;

import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public interface UsuarioService {
    void iniciarSesion(Credencial credencial);
    void solicitarFotocopiar(Solicitud solicitud);
    List<Solicitud> verHistorialSolicitudes();
}
