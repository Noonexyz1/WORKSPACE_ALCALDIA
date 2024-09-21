package com.prototipo.domain.enums;

public enum EstadoSolicitudEnum {
    PENDIENTE("Pendiente"),
    COMPLETADA("Completada");

    private final String nombre;

    EstadoSolicitudEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
