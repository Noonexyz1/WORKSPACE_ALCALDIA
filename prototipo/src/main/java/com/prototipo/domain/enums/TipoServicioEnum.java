package com.prototipo.domain.enums;

public enum TipoServicioEnum {
    FOTOCOPIA("Fotocopia"),
    ANILLADO("Anillado");

    private final String nombre;

    TipoServicioEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
