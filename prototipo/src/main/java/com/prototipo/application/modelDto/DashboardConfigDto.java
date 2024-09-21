package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardConfigDto {

    private Long id;
    private String nombreComponente;
    private RolDto fkRol;
}
