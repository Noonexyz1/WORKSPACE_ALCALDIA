package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredencialDto {

    private Long id;
    private String nombreUser;
    private String pass;
    private UsuarioDto fkUsuario;
}
