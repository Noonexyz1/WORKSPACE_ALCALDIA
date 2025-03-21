package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoRetiro {
    private Long id;
    private Long disponible;
    private Long usado;
    private Long total;
}
