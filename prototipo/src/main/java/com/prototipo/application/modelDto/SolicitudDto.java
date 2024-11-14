package com.prototipo.application.modelDto;

import com.prototipo.domain.model.Unidad;
import com.prototipo.domain.model.Usuario;
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

    private String cite;

    private Usuario fkSolicitante;
    private Unidad fkUnidad;
}
