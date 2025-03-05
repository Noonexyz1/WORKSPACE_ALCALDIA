package com.prototipo.infrastructure.http.rest.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredencialRequest {
    private String ci;
    private String pass;
}
