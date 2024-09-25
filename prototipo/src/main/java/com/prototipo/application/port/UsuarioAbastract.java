package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioDto;

public interface UsuarioAbastract {
    UsuarioDto findUsuarioPorIdAbastract(Long idUsuario);
}
