package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nombres;
    private String paterno;
    private String materno;
    private String correo;
    private String ci;
    private String formacion;

    private String nombreRol;
    private String nombreUnidad;
    private String nombreCargo;
}
