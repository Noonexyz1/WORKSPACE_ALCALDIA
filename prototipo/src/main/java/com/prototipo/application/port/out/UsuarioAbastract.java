package com.prototipo.application.port.out;

import com.prototipo.domain.model.Usuario;

public interface UsuarioAbastract {
    Usuario guardarUsuarioAbastract(Usuario usuario);
    Usuario iniciarSesionAbstract(String ci);
}
