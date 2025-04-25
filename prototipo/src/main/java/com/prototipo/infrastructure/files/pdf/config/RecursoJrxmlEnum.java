package com.prototipo.infrastructure.files.pdf.config;

public enum RecursoJrxmlEnum {
    //Aqui van las plantillas para el reporte PDF
    ORDEN_JRXML("templates/report/orden.jrxml"),
    COMUNICACION_JRXML("templates/report/comunicacion.jrxml"),
    SOLICITUD_JRXML("templates/report/solicitud.jrxml"),
    NOTAPEDIDO_JRXML("templates/report/notaPedido.jrxml"),
    REPORTE_JRXML("templates/report/reporte.jrxml"),
    INFORME_JRXML("templates/report/informe.jrxml");

    private final String recursoJrxml;

    RecursoJrxmlEnum(String parametro) {
        this.recursoJrxml = parametro;
    }

    public String getParametro() {
        return this.recursoJrxml;
    }
}
