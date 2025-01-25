package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioUnidadDto;

import java.util.List;

public interface UsuarioUnidadAbstract {
    UsuarioUnidadDto guardarUsuarioUnidad(UsuarioUnidadDto usuarioUnidadDto);
    UsuarioUnidadDto encontrarUsuarioUnidadByUsuarioId(Long idUsuario);
    UsuarioUnidadDto encontarUsuarioUnidadId(Long idUsuarioUnidad);
}
