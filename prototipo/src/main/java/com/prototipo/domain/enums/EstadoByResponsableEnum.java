package com.prototipo.domain.enums;

public enum EstadoByResponsableEnum {
    PENDIENTE("Pendiente"),
    APROBADA("Aprobada"),
    RECHAZADA("Rechazada");

    private final String nombre;

    EstadoByResponsableEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
