package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolResponse {

    private Long id;
    private String nombreRol;
}
