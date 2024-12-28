package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    //private Boolean isActive;

    private Long fkUsuario;
    private Long fkUnidad;

    private Long fkRol;
    private String nombreRol;
    private String dashConfig;

    private Long fkCargo;
    private Long fkResponsable;

    private String nombreUsuario;
    private String apellidoUsuario;
}
