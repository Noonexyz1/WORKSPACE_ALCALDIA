package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.port.ArchivoPdfAbstract;
import com.prototipo.infrastructure.persistence.db.entity.ArchivoPdf;
import com.prototipo.infrastructure.persistence.db.repository.ArchivoPdfRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArchivoPdfImpl implements ArchivoPdfAbstract {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ArchivoPdfRepository archivoPdfRepository;

    @Override
    public List<ArchivoPdfDto> listaDePdfsById(Long idSolicitud) {
        List<ArchivoPdf> listArchivos = archivoPdfRepository.findAllByFkSolicitud_Id(idSolicitud);
        return listArchivos.stream()
                .map(x -> mapper.map(x, ArchivoPdfDto.class))
                .toList();
    }
}
