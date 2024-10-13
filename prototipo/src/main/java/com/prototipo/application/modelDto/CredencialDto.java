package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredencialDto {
    private Long id;
    private String correo;
    private String pass;
    private UsuarioDto fkUsuario;
}
