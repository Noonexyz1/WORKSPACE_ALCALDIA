package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.persistence.FotocopiaAbstract;
import com.prototipo.domain.model.Fotocopia;
import com.prototipo.infrastructure.persistence.db.entity.FotocopiaEntity;
import com.prototipo.infrastructure.persistence.db.repository.DetalleSolicitudRepository;
import com.prototipo.infrastructure.persistence.db.repository.FotocopiaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Fotocopia> getFotocopiasSolicitudAbstract(Long idSolicitud) {
        List<FotocopiaEntity> list = detalleSolicitudRepository
                .findAllFotocopiaByIdSoli(idSolicitud);
        return list.stream()
                .map(x -> modelMapper.map(x, Fotocopia.class))
                .toList();
    }

    @Override
    @Transactional
    public void guardarRegistroFotocopia(Fotocopia fotocopia) {
        FotocopiaEntity fotocopiaEntity = modelMapper
                .map(fotocopia, FotocopiaEntity.class);
        fotocopiaRepository.save(fotocopiaEntity);
    }
}
