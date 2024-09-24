package com.prototipo.domain.enums;

public enum EstadoByOperadorEnum {
    PENDIENTE("Pendiente"),
    INICIADO("Iniciado"),
    COMPLETADO("Completado");

    private final String nombre;

    EstadoByOperadorEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
