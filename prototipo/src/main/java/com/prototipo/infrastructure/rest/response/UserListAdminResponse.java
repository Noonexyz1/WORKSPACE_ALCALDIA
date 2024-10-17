package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Añade esto para que el distinct() de Streams pueda determinar si los objetos son repetidos
@EqualsAndHashCode
public class UserListAdminResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String nombreRol;
    private String nombreUnidad;
}
