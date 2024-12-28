package com.prototipo.infrastructure.rest.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private int page;             // Número de página actual
    private int size;             // Tamaño de la página
    private String sortBy;        // Campo de ordenamiento
    private String direction;     // Dirección del ordenamiento

    private List<T> content;      // Los elementos de la página actual
    private int totalPages;       // Total de páginas disponibles
    private long totalElements;   // Total de elementos en la BD
}
