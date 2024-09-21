package com.prototipo.domain.enums;

public enum NotificacionEnum {
    PENDIENTE("Pendiente"),
    APROBADA("Aprobada"),
    RECHAZADA("Rechazada");

    private final String nombre;

    NotificacionEnum(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
