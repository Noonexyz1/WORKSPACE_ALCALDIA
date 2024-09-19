package com.prototipo.application.modelDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArchivoPdfDto {
    private Long id;
    private String archivo;
}
