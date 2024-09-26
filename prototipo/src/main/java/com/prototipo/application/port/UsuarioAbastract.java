package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioDto;

import java.util.List;

public interface UsuarioAbastract {
    UsuarioDto findUsuarioPorIdAbastract(Long idUsuario);
    List<UsuarioDto> listaDeUsuarios();
}
