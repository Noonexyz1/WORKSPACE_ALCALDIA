package com.prototipo.application.modelDto;

import com.prototipo.domain.model.SolicitudDomain;
import com.prototipo.infrastructure.persistence.db.entity.Solicitud;
import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperacionDto {
    private Long id;
    private String estadoByOperador;
    private SolicitudDto fkSolicitud;
    private UsuarioDto fkOperador;
}
