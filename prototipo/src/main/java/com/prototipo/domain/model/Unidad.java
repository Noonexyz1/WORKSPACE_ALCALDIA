package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Unidad {
    private Long id;
    private String nombre;
    private String direccion;
}
