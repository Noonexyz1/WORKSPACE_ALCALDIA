package com.prototipo.application.useCase;

import com.prototipo.domain.model.CredencialDomain;
import com.prototipo.domain.model.UsuarioDomain;

import java.util.List;

public interface InicioSesionService {
    UsuarioDomain iniciarSesionService(CredencialDomain credencial);
    String configuracionDeUsuarioService(Long idRolUsuario);
    String rolDeUsuarioService();
}
