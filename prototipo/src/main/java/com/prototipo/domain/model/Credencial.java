package com.prototipo.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Credencial {

    private Long id;
    private String nombreUser;
    private String pass;

    private Usuario usuario;
}

