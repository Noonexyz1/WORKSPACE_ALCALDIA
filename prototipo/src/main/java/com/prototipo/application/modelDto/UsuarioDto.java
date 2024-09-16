package com.prototipo.application.modelDto;

import com.prototipo.domain.enums.Rol;
import com.prototipo.domain.model.Credencial;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsuarioDto {

    private Long id;
    private String nombres;
    private String apellidos;

    private UsuarioDto responsable;
    private Credencial credencial;
    private Rol rol;
}
