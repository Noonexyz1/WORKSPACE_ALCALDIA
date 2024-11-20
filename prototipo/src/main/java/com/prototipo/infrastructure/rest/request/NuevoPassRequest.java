package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NuevoPassRequest {
    private String ci;
    private String pass;
    private String nuevoPass;
}
