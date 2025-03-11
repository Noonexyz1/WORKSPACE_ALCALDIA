package com.prototipo.application.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginableIn {
    private Long id;

    private Long page;
    private Long size;
    private String sortBy;
    private String direction;
}
