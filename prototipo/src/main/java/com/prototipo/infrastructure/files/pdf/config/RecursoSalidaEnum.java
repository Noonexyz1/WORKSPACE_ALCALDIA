package com.prototipo.infrastructure.files.pdf.config;

public enum RecursoSalidaEnum {
    //Aqui van las plantillas para el reporte PDF
    ORDEN_PDF("/home/kali/Downloads/ordenPDF"),
    INFORME_PDF("/home/kali/Downloads/informePDF"),
    COMUNICACION_PDF("/home/kali/Downloads/comunicacionPDF"),
    SOLICITUD_PDF("/home/kali/Downloads/solicitudPDF"),
    NOTA_PDF("/home/kali/Downloads/notaPedidoPDF"),
    REPORTE_PDF("/home/kali/Downloads/reportePDF");

    private final String recursoSalida;

    RecursoSalidaEnum(String parametro) {
        this.recursoSalida = parametro;
    }

    public String getParametro() {
        return this.recursoSalida;
    }
}
