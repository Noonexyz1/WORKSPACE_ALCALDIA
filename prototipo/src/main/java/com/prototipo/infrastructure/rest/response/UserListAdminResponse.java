package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserListAdminResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String nombreRol;
}
