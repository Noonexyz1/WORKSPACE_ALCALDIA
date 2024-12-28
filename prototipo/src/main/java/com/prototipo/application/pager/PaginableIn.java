package com.prototipo.application.pager;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginableIn {
    private Long page;
    private Long size;
    private String sortBy;
    private String direction;
}
