package com.prototipo.application.port;

import com.prototipo.domain.model.Credencial;

public interface CredencialPersist {
    void buscarUsuario(Credencial credencial);
}
