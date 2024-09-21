package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsumoDto {

    private Long id;
    private String tipo_reporte;
    private String fecha_reporte;
    private String formato; //ENUM('PDF', 'Excel') NOT NULL
    private String detalle;
}
