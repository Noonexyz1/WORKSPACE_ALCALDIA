package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioDto;

public interface UsuarioAbastract {
    UsuarioDto guardarUsuarioAbastract(UsuarioDto usuarioDto);
    UsuarioDto iniciarSesionAbstract(String correo, String pass);
}
