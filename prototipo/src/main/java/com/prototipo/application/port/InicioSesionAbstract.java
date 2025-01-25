package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioDto;

public interface InicioSesionAbstract {
    UsuarioDto iniciarSesionAbstract(String correo, String pass);
}
