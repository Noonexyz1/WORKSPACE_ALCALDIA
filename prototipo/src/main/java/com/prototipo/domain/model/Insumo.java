package com.prototipo.domain.model;

public class Insumo {

    private Long id;

    private String tipo_reporte;
    private String fecha_reporte;
    private String formato; //ENUM('PDF', 'Excel') NOT NULL
    private String detalle;

    private Solicitud solicitud;
}
