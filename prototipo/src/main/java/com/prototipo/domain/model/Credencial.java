package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credencial {
    private Long id;
    private String ci;
    private String pass;
    private Usuario fkUsuario;
}
