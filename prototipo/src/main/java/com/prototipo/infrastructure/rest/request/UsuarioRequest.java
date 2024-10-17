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
    private String correo;
}
