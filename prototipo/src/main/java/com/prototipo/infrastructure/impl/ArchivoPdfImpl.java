package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.port.ArchivoPdfAbstract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArchivoPdfImpl implements ArchivoPdfAbstract {

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ArchivoPdfDto> listaDePdfsById(Long idSolicitud) {

        return null;
    }
}
