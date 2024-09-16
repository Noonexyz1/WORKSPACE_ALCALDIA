package com.prototipo.domain.model;

import com.prototipo.domain.enums.Rol;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Usuario {

    private Long id;
    private String nombres;
    private String apellidos;

    private Usuario responsable;
    private Credencial credencial;
    private Rol rol;
}
