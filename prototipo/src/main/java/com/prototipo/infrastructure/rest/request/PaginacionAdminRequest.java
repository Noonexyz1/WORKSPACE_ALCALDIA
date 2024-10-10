package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginacionAdminRequest {
    private Long page;
    private Long size;
    private String byColumName;
}