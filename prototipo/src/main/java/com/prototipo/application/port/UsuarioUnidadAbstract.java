package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioUnidadDto;

public interface UsuarioUnidadAbstract {
    UsuarioUnidadDto guardarUsuarioUnidad(UsuarioUnidadDto usuarioUnidadDto);
    UsuarioUnidadDto encontrarUsuarioUnidadByUsuarioId(Long idUsuario);
}
