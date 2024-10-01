package com.prototipo.application.useCase;

import com.prototipo.domain.model.CredencialDomain;

public interface CredencialService {
    void buscarUsuarioService(CredencialDomain credencial);
    CredencialDomain guardarCredencialService(CredencialDomain nuevaCred);
}
