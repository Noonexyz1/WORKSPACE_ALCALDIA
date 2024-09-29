package com.prototipo.infrastructure.rest.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponse {
    private String nombres;
    private String apellidos;
    private String correo;
    private Boolean isActive;
    private String nombreRol;
    private List<String> listDashConfig;
}
