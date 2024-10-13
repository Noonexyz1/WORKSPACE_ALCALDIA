package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArchivoPdfResponse {
    private String nombreArchivo;
    private String archivo;
}
