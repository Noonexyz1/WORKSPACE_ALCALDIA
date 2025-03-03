package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.FotocopiaDto;
import com.prototipo.application.port.FotocopiaAbstract;
import com.prototipo.infrastructure.persistence.db.entity.FotocopiaEntity;
import com.prototipo.infrastructure.persistence.db.repository.DetalleSolicitudRepository;
import com.prototipo.infrastructure.persistence.db.repository.FotocopiaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FotocopiaImpl implements FotocopiaAbstract {

    @Autowired
    private DetalleSolicitudRepository detalleSolicitudRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FotocopiaRepository fotocopiaRepository;

    @Override
    public List<FotocopiaDto> getFotocopiasSolicitudAbstract(Long idSolicitud) {
        List<FotocopiaEntity> list = detalleSolicitudRepository
                .findAllFotocopiaByIdSoli(idSolicitud);
        return list.stream()
                .map(x -> modelMapper.map(x, FotocopiaDto.class))
                .toList();
    }
    @Override
    public void guardarRegistroFotocopia(FotocopiaDto fotocopiaDto) {
        FotocopiaEntity fotocopiaEntity = modelMapper
                .map(fotocopiaDto, FotocopiaEntity.class);
        fotocopiaRepository.save(fotocopiaEntity);
    }
}
