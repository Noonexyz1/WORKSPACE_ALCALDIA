package com.prototipo.application.modelDto;

import com.prototipo.domain.model.Usuario;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CredencialDto {

    private Long id;
    private String nombreUser;
    private String pass;

    private Usuario usuario;
}
