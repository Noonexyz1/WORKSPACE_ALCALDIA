package com.prototipo.application.useCase;

import com.prototipo.domain.model.UsuarioDomain;

import java.util.List;

public interface UsuarioService {
    UsuarioDomain findUsuarioPorIdService(Long idUnidad);
    List<UsuarioDomain> listaDeUsuarios();
}
