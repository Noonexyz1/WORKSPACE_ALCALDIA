package org.example.application.usecase;

import org.example.domain.model.Credencial;
import org.example.domain.model.Solicitud;

import java.util.List;

public interface UsuarioService {
    void iniciarSesion(Credencial credencial);
    void solicitarFotocopiar(Solicitud solicitud);
    List<Solicitud> verHistorialSolicitudes();
}
