package com.prototipo.infrastructure.http.rest.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolResponse {
    private Long id;
    private String nombreRol;
}
