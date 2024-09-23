package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;
    private String nombres;
    private String apellidos;
    // Relación reflexiva: un empleado puede tener un gerente
    private UsuarioDto fkResponsable;
    private RolDto fkRol;
}
