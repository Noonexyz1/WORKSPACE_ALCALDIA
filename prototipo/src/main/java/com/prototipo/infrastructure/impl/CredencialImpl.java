package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.port.CredencialAbstract;
import com.prototipo.infrastructure.persistence.db.entity.CredencialEntity;
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
        CredencialEntity credencialEntity = modelMapper.map(nuevaCred, CredencialEntity.class);
        CredencialEntity credencialEntityResp = credencialRepository.save(credencialEntity);
        return modelMapper.map(credencialEntityResp, CredencialDto.class);
    }

    @Override
    public CredencialDto encontrarCredencial(String correo, String pass) {
        //TODO??
        return null;
    }

    @Override
    public CredencialDto encontrarCredencialPorUsuarioId(Long idUsuario) {
        CredencialEntity credencialEntity = credencialRepository.encontrarCredencialPorUsuarioId(idUsuario);
        return (credencialEntity != null)? modelMapper.map(credencialEntity, CredencialDto.class): null;
    }
}
