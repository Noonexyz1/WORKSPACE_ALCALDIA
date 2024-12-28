package com.prototipo.application.useCase;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.Usuario;
import com.prototipo.domain.model.UsuarioUnidad;

import java.util.List;

public interface UsuarioService {
    Usuario findUsuarioPorIdService(Long idUnidad);

    PaginableOut<UsuarioUnidad> listaDeUsuariosServiceDef(PaginableIn paginableIn);
    List<Usuario> listaDeUsuariosServiceAsc(Long page, Long size, String byColumName);
    List<Usuario> listaDeUsuariosServiceDesc(Long page, Long size, String byColumName);
    UsuarioUnidad findUsuarioUnidadByIdUSer(Long id);
}
