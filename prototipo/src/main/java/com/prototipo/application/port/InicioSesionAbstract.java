package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioUnidadDto;

public interface InicioSesionAbstract {
    UsuarioUnidadDto iniciarSesionAbstract(String correo, String pass);
    String rolDeUsuarioAbstract();
}
