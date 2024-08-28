package com.prototipo.application.impl;

import com.prototipo.application.port.CredencialPersist;
import com.prototipo.application.port.SolicitudPersist;
import com.prototipo.application.port.TokenAbstract;
import com.prototipo.application.port.UsuarioService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class UsuarioImpl implements UsuarioService {

    private CredencialPersist credencialPersist;
    private TokenAbstract tokenAbstract;
    private SolicitudPersist solicitudPersist;

    public UsuarioImpl(CredencialPersist credencialPersist,
                       TokenAbstract tokenAbstract,
                       SolicitudPersist solicitudPersist) {

        this.credencialPersist = credencialPersist;
        this.tokenAbstract = tokenAbstract;
        this.solicitudPersist = solicitudPersist;
    }


    @Override
    public void iniciarSesion(Credencial credencial) {
        this.credencialPersist.buscarUsuario(credencial);
        this.tokenAbstract.generarToken();

    }

    @Override
    public void solicitarFotocopiar(Solicitud solicitud) {
        this.solicitudPersist.solicitarFotocopiar(solicitud);
    }

    @Override
    public List<Solicitud> verHistorialSolicitudes() {
        return this.solicitudPersist.getListaSolicitudes();
    }
}
