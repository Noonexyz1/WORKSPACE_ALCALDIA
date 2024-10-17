package com.prototipo.application.useCase;

import com.prototipo.domain.model.Usuario;
import com.prototipo.domain.model.UsuarioUnidad;

import java.util.List;

public interface UsuarioService {
    Usuario findUsuarioPorIdService(Long idUnidad);
    List<UsuarioUnidad> listaDeUsuariosServiceDef(Long page, Long size);
    List<Usuario> listaDeUsuariosServiceAsc(Long page, Long size, String byColumName);
    List<Usuario> listaDeUsuariosServiceDesc(Long page, Long size, String byColumName);
}
