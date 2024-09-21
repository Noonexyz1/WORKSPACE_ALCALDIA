package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPdfDto {

    private Long id;
    private String archivo;
    private SolicitudDto fkSolicitud;
}
