package com.prototipo.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArchivoPdf {
    private Long id;
    private String archivo;
}
