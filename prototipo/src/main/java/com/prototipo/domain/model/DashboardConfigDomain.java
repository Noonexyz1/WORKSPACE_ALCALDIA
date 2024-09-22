package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardConfigDomain {
    private Long id;
    private String nombreComponente;
    private RolDomain fkRol;
}
