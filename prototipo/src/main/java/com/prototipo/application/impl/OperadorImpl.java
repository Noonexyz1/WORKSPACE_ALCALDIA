package com.prototipo.application.impl;

import com.prototipo.application.port.OperadorService;
import com.prototipo.application.port.UsuarioService;
import com.prototipo.domain.enums.EstadoSolicitud;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class OperadorImpl implements OperadorService, UsuarioService {


    @Override
    public List<Solicitud> verSolicitudes() {
        return List.of();
    }

    @Override
    public boolean cambiarEstadoDeSolicitud(EstadoSolicitud nuevoEstado) {
        return false;
    }

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
