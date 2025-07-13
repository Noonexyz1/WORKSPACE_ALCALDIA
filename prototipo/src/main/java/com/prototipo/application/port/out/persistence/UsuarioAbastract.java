package com.prototipo.application.port.out.persistence;

import com.prototipo.domain.model.Usuario;

public interface UsuarioAbastract {
    Usuario guardarUsuarioAbastract(Usuario usuario);
    Usuario iniciarSesionAbstract(String ci);

    Usuario encontrarUsuarioPorId(Long id);
}
