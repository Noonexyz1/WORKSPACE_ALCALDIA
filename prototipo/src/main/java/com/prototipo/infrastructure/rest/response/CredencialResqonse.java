package com.prototipo.infrastructure.rest.response;

import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import lombok.Builder;

@Builder
public class CredencialResqonse {

    private Long id;
    private String correo;
    private String password;

    private Usuario usuario;
}
