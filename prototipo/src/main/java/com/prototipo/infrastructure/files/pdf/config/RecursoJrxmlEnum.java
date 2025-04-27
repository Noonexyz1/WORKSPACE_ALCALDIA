package com.prototipo.infrastructure.files.pdf.config;

import java.io.InputStream;

public enum RecursoJrxmlEnum {
    //Aqui van las plantillas para el reporte PDF
    SOLICITUD_JRXML("templates/report/solicitud.jrxml"),
    COMUNICACION_JRXML("templates/report/comunicacion.jrxml"),
    INFORME_JRXML("templates/report/informe.jrxml"),
    ORDEN_JRXML("templates/report/orden.jrxml"),
    NOTAPEDIDO_JRXML("templates/report/notaPedido.jrxml"),
    REPORTE_JRXML("templates/report/reporte.jrxml");

    private final String recursoJrxml;

    RecursoJrxmlEnum(String parametro) {
        this.recursoJrxml = parametro;
    }

    public InputStream asInputStream() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(recursoJrxml);
        if (inputStream == null) {
            throw new RuntimeException("No se pudo encontrar el recurso: " + recursoJrxml);
        }
        return inputStream;
    }
}
