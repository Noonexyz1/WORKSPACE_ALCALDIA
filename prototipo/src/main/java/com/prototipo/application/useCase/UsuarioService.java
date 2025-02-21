package com.prototipo.application.useCase;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.UsuarioUnidad;

public interface UsuarioService {
    PaginableOut<UsuarioUnidad> listaDeUsuariosServiceDef(PaginableIn paginableIn);
}
