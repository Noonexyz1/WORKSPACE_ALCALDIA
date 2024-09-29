package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.port.CredencialAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Credencial;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CredencialImpl implements CredencialAbstract {

    @Autowired
    private CredencialRepository credencialRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CredencialDto guardarCredencialAbstract(CredencialDto nuevaCred) {
        Credencial credencial = modelMapper.map(nuevaCred, Credencial.class);
        Credencial credencialResp = credencialRepository.save(credencial);
        return modelMapper.map(credencialResp, CredencialDto.class);
    }
}
