package com.prototipo.application.port.out;

import com.prototipo.application.modelDto.UsuarioDto;

public interface UsuarioAbastract {
    UsuarioDto guardarUsuarioAbastract(UsuarioDto usuarioDto);
    UsuarioDto iniciarSesionAbstract(String correo, String pass);
}
