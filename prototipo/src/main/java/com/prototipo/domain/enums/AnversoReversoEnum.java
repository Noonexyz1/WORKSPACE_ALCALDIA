package com.prototipo.domain.enums;

public enum AnversoReversoEnum {
    ANVERSO("Anverso"),
    ANVERSO_REVERSO("Anverso y reverso");

    private final String nombre;

    AnversoReversoEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
