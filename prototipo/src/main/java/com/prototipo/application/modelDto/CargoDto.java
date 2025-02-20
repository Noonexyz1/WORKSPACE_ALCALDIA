package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoDto {
    private Long id;
    private String nombreCargo;

    //Esto lo estoy dejando a pesar de que esta es una tabla fuerte
    //private List<UsuarioUnidad> listUsuarioUnidad;
    //ESTO LO COMENTO PORQUE NO LO PUEDE MAPEAR
}
