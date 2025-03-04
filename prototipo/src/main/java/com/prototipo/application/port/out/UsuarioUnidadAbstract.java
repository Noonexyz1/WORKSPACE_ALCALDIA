package com.prototipo.application.port.out;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.UsuarioUnidad;

public interface UsuarioUnidadAbstract {
    UsuarioUnidad guardarUsuarioUnidad(UsuarioUnidad usuarioUnidad);
    UsuarioUnidad encontrarUsuarioUnidadByUsuarioId(Long idUsuario);
    UsuarioUnidad encontarUsuarioUnidadId(Long idUsuarioUnidad);
    UsuarioUnidad encontrarUsuarioUnidadByCi(String ci);
    PaginableOut<UsuarioUnidad> listaDeUsuariosAbsDef(PaginableIn paginableIn);
}
