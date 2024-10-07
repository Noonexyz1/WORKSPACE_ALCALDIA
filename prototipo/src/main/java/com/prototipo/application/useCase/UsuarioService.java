package com.prototipo.application.useCase;

import com.prototipo.domain.model.UsuarioDomain;

import java.util.List;

public interface UsuarioService {
    UsuarioDomain findUsuarioPorIdService(Long idUnidad);
    List<UsuarioDomain> listaDeUsuariosServiceDef(Long page, Long size);
    List<UsuarioDomain> listaDeUsuariosServiceAsc(Long page, Long size, String byColumName);
    List<UsuarioDomain> listaDeUsuariosServiceDesc(Long page, Long size, String byColumName);
}
