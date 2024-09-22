package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDto {

    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    private String estadoByResponsable;
    private String estadoByOperador;
    private UsuarioDto fkSolicitante;
    private UnidadDto fkUnidad;
}
