package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioDto;

public interface InicioSesionAbstract {
    //TODO, ya hay una interfaz llamada Autorizacion en los use cases
    UsuarioDto iniciarSesionAbstract(String correo, String pass);
}
