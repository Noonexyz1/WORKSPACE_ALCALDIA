package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDomain {
    private Long id;
    private String nombres;
    private String apellidos;
    private UsuarioDomain fkResponsable;
    private RolDomain fkRol;
}
