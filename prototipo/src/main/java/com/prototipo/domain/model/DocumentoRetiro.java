package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoRetiro {
    private Long id;
    private Long totalCopia;
    private Long totalUsado;
    private Long totalDisponible;

    private String fecha;

    private Fotocopia fkFotocopia;
}
