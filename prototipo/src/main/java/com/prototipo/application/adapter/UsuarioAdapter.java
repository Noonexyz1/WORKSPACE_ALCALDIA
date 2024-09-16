package com.prototipo.application.adapter;

import com.prototipo.application.useCase.CredencialPersist;
import com.prototipo.application.useCase.SolicitudPersist;
import com.prototipo.application.useCase.TokenAbstract;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class UsuarioAdapter implements UsuarioService {

    private CredencialPersist credencialPersist;
    private TokenAbstract tokenAbstract;
    private SolicitudPersist solicitudPersist;

    public UsuarioAdapter(CredencialPersist credencialPersist,
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
