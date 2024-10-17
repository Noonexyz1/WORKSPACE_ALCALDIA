package com.prototipo.infrastructure.rest.response;

import com.prototipo.infrastructure.persistence.db.entity.UsuarioEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredencialResqonse {

    private Long id;
    private String correo;
    private String password;

    private UsuarioEntity usuarioEntity;
}
