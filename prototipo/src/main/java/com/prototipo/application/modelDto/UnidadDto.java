package com.prototipo.application.modelDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnidadDto {

    private Long id;
    private String nombre;
    private String direccion;
}
