package com.prototipo.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Unidad {

    private Long id;
    private String nombre;
    private String direccion;
}
