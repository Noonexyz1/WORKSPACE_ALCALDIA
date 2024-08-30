package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRequest {

    private Long id;
    private String nombres;
    private String apellidos;

    // Relación reflexiva: un empleado puede tener un gerente
    private Long fk_responsable;
    private Long fk_rol;
}
