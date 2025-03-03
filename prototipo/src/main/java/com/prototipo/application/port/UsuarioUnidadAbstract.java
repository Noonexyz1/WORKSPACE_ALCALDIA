package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

public interface UsuarioUnidadAbstract {
    UsuarioUnidadDto guardarUsuarioUnidad(UsuarioUnidadDto usuarioUnidadDto);
    UsuarioUnidadDto encontrarUsuarioUnidadByUsuarioId(Long idUsuario);
    UsuarioUnidadDto encontarUsuarioUnidadId(Long idUsuarioUnidad);
    UsuarioUnidadDto encontrarUsuarioUnidadByCi(String ci);
    PaginableOut<UsuarioUnidadDto> listaDeUsuariosAbsDef(PaginableIn paginableIn);
}
