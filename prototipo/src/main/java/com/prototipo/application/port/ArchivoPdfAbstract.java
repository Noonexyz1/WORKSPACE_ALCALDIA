package com.prototipo.application.port;

import com.prototipo.application.modelDto.ArchivoPdfDto;

import java.util.List;

public interface ArchivoPdfAbstract {
    List<ArchivoPdfDto> listaDePdfsById(Long idSolicitud);
}
