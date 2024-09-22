package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardConfigDto {

    private Long id;
    private String nombreComponente;
    private RolDto fkRol;
}
