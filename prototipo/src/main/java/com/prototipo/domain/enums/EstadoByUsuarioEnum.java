package com.prototipo.domain.enums;

public enum EstadoByUsuarioEnum {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String nombre;

    EstadoByUsuarioEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
