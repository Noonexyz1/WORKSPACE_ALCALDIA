package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
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
