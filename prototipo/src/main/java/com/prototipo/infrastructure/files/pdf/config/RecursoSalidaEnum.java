package com.prototipo.infrastructure.files.pdf.config;

import java.io.File;

public enum RecursoSalidaEnum {
    //Aqui van la direccion para exporta los PDF
    SOLICITUD_PDF("/home/kali/Downloads/solicitudPDF"),
    COMUNICACION_PDF("/home/kali/Downloads/comunicacionPDF"),
    INFORME_PDF("/home/kali/Downloads/informePDF"),
    ORDEN_PDF("/home/kali/Downloads/ordenPDF"),
    NOTA_PDF("/home/kali/Downloads/notaPedidoPDF"),
    REPORTE_PDF("/home/kali/Downloads/reportePDF");

    private final String recursoSalida;

    RecursoSalidaEnum(String parametro) {
        this.recursoSalida = parametro;
    }

    public String getParametro(String nombreDocu) {
        // Crear el directorio si no existe
        File outputDir = new File(recursoSalida);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        return this.recursoSalida + "/" + nombreDocu + ".pdf";
    }
}
