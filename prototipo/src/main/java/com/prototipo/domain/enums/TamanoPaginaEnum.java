package com.prototipo.domain.enums;

public enum TamanoPaginaEnum {
    CARTA("Carta"),
    OFICIO("Oficio");

    private final String nombre;

    TamanoPaginaEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
