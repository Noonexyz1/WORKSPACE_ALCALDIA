package com.prototipo.application.useCase;

import com.prototipo.domain.model.CredencialDomain;

public interface CredencialPersist {
    void buscarUsuario(CredencialDomain credencial);
}
