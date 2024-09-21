package com.prototipo.domain.enums;

public enum RolEnum {
    ADMINISTRADOR("Administrador"),
    SOLICITANTE("Solicitante"),
    OPERADOR("Operador");

    private final String nombre;

    RolEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
