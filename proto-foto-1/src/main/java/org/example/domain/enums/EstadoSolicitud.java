package org.example.domain.enums;

public enum EstadoSolicitud {
    PENDIENTE("Pendiente"),
    COMPLETADA("Completada");

    private final String nombre;

    EstadoSolicitud(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
