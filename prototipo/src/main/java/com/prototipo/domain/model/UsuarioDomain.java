package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDomain {
    private Long id;
    private String nombres;
    private String apellidos;
    private RolDomain fkRol;
    //private CredencialDomain credencial;
}
