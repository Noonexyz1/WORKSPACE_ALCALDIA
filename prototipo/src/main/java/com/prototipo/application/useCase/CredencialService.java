package com.prototipo.application.useCase;

import com.prototipo.domain.model.Credencial;

public interface CredencialService {
    void buscarUsuarioService(Credencial credencial);
    Credencial guardarCredencialService(Credencial nuevaCred);
}
