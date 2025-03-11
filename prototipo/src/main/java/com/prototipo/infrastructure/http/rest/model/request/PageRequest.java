package com.prototipo.infrastructure.http.rest.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequest {
    private Long id;

    private Long page;
    private Long size;
    private String sortBy;
    private String direction;
}
