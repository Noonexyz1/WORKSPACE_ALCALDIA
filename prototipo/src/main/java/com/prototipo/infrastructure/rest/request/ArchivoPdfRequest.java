package com.prototipo.infrastructure.rest.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArchivoPdfRequest {
    private String nombreArchivo;
    private String archivo;
}
