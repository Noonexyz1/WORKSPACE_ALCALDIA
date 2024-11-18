package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Añade esto para que el distinct() de Streams pueda determinar si los objetos son repetidos
//@EqualsAndHashCode
public class UsuarioUnidadSesionResponse {

    private Long id;
    //private Boolean isActive;

    private Long fkUsuario;
    private Long fkUnidad;

    private Long fkRol;
    private String nombreRol;
    private String dashConfig;

    private Long fkCargo;
    private Long fkResponsable;
}
