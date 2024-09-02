package com.prototipo.infrastructure.rest.response;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
    private String nombres;
    private String apellidos;

    // Relación reflexiva: un empleado puede tener un gerente
    @ManyToOne
    private UsuarioResponse fk_responsable;

    @ManyToOne
    private RolResponse fk_rol;

    private List<DashboardConfigResponse> listDashConfig;

}
