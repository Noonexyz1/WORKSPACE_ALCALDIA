package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Añade esto para que el distinct() de Streams pueda determinar si los objetos son repetidos
//@EqualsAndHashCode
public class UsuarioUnidadResponse {
    private Long id;
    private Boolean isActive;

    private Long idUser;
    private String nombres;
    private String materno;
    private String paterno;
    private String correo;
    private String ci;

    private String nombreRol;
    private String nombreUnidad;
    private String nombreCargo;

    private Long idRol;
    private Long idUni;
    private Long idCargo;
    private Long idResponsable;
    private Long idDirector;
}
