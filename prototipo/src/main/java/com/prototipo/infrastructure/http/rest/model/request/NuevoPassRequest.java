package com.prototipo.infrastructure.http.rest.model.request;

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
