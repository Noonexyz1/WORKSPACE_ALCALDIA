package com.prototipo.application.pager;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginableOut<T> {
    private List<T> content;      // Los elementos de la página actual
    private int totalPages;       // Total de páginas disponibles
    private long totalElements;   // Total de elementos en la BD
}
