package com.prototipo.infrastructure.rest.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsableListRequest {
    private Long idResponsable;
    private String nombreUnidad;
}
