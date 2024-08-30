package com.prototipo.infrastructure.rest.request;

import lombok.Builder;

@Builder
public class CredencialRequest {
    private String correo;
    private String password;
}
