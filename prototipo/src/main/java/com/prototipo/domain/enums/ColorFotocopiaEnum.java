package com.prototipo.domain.enums;

public enum ColorFotocopiaEnum {
    BLANCO_NEGRO("Blanco y negro"),
    COLOR("Color");

    private final String nombre;

    ColorFotocopiaEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
