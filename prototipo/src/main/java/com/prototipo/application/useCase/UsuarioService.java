package com.prototipo.application.useCase;

import com.prototipo.domain.model.UsuarioDomain;

public interface UsuarioService {
    UsuarioDomain findUsuarioPorIdService(Long idUnidad);
}
