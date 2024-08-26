package com.prototipo.application.impl;

import com.prototipo.application.port.UsuarioService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class SolicitanteImpl implements UsuarioService {


    @Override
    public void iniciarSesion(Credencial credencial) {

    }

    @Override
    public void solicitarFotocopiar(Solicitud solicitud) {

    }

    @Override
    public List<Solicitud> verHistorialSolicitudes() {
        return List.of();
    }
}
