package com.prototipo.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {
    private Long id;
    private String nombreCargo;

    //Esto lo estoy dejando a pesar de que esta es una tabla fuerte
    private List<UsuarioUnidad> listUsuarioUnidad;
}
