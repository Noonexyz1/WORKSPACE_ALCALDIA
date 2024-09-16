package com.prototipo.application.port;

import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.modelDto.UsuarioDto;

import java.util.List;

public interface InicioSesionAbstract {

    UsuarioDto iniciarSesionAbstract(CredencialDto credencialDto);
    String rolDeUsuarioAbstract();
    List<String> configuracionDeUsuarioAbstract();
}
