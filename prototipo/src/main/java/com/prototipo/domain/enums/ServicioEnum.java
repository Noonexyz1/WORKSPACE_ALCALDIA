package com.prototipo.domain.enums;

public enum ServicioEnum {
    FOTOCOPIA("Fotocopia");

    private final String nombre;

    ServicioEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
