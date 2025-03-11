package com.prototipo.infrastructure.http.rest.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String nombres;
    private String paterno;
    private String materno;
    private String ci;
    private String correo;
    private String nombreRol;
    private String nombreUnidad;
    private String nombreCargo;
}
