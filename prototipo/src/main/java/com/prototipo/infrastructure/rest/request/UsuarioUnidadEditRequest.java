package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioUnidadEditRequest {
    private Long id;
    private String nombres;
    private String materno;
    private String paterno;
    private String correo;
    private String ci;

    private Long idRol;
    private Long idUni;
    private Long idCargo;
    private Long idResponsable;
    private Long idDirector;
}
