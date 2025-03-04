package com.prototipo.application.port.out;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.domain.model.UsuarioUnidad;

public interface UsuarioUnidadAbstract {
    UsuarioUnidad guardarUsuarioUnidad(UsuarioUnidad usuarioUnidad);
    UsuarioUnidad encontrarUsuarioUnidadByUsuarioId(Long idUsuario);
    UsuarioUnidad encontarUsuarioUnidadId(Long idUsuarioUnidad);
    UsuarioUnidad encontrarUsuarioUnidadByCi(String ci);
    PaginableOut<UsuarioUnidad> listaDeUsuariosAbsDef(PaginableIn paginableIn);
}
