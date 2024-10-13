package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.SolicitanteDto;
import com.prototipo.application.port.SolicitanteAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Solicitante;
import com.prototipo.infrastructure.persistence.db.repository.SolicitanteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolicitanteImpl implements SolicitanteAbstract {

    @Autowired
    private SolicitanteRepository solicitanteRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SolicitanteDto guardarSolicitanteAbstract(SolicitanteDto nuevoSolicitante) {
        Solicitante newSoli = modelMapper.map(nuevoSolicitante, Solicitante.class);
        Solicitante soliciReso = solicitanteRepository.save(newSoli);
        return modelMapper.map(soliciReso, SolicitanteDto.class);
    }
}
