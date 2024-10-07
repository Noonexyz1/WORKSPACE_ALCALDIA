package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioSoliRequest {
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private Long idRol;
}
