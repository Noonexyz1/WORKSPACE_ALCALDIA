package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPdfDto {

    private Long id;
    private String archivo;
    private SolicitudDto fkSolicitud;
}
