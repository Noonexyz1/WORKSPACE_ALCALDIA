package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    private Long id;
    private String cite;
    private String descripcion;
    private String fecha;
    private Long autoriFlag;
    private Boolean isActive;

    private Double precioTotal;
    private String nombreServicio;

    private Long paginaTotal;
    private Long copiaTotal;

    private UsuarioUnidad fkUsuarioSolicitante;
}
