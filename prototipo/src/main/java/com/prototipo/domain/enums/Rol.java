package com.prototipo.domain.enums;

public enum Rol {
    ADMINISTRADOR("Administrador"),
    SOLICITANTE("Solicitante"),
    OPERADOR("Operador");

    private final String nombre;

    Rol(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
