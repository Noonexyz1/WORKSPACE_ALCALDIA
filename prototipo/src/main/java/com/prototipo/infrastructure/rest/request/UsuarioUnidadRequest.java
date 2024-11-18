package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioUnidadRequest {
    private Long id;
    private Boolean isActive;

    private Long idUser;
    private String nombres;
    private String paterno;
    private String materno;
    private String correo;

    private Long idUni;
    private Long idRol;
    private Long idCargo;
}
