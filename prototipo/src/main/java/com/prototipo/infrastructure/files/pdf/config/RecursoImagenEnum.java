package com.prototipo.infrastructure.files.pdf.config;

public enum RecursoImagenEnum {
    //Aqui va la ruta de donde estan las imagenes para el PDF
    PATH_IMAGEN("classpath:/static/images/");

    private final String recursoImagen;

    RecursoImagenEnum(String parametro) {
        this.recursoImagen = parametro;
    }

    public String getParametro() {
        return this.recursoImagen;
    }
}
