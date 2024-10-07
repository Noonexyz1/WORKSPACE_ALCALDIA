package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioOpeRequest {
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private Long idRol;
    private String pisoAsignado;
}
