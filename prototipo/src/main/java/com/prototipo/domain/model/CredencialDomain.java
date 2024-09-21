package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredencialDomain {
    private Long id;
    private String nombreUser;
    private String pass;
    private UsuarioDomain fkUsuario;
}
