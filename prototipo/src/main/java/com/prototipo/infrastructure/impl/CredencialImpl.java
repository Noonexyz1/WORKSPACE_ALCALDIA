package com.prototipo.infrastructure.impl;

import com.prototipo.application.port.out.CredencialAbstract;
import com.prototipo.domain.model.Credencial;
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
    public Credencial guardarCredencialAbstract(Credencial nuevaCred) {
        CredencialEntity credencialEntity = modelMapper.map(nuevaCred, CredencialEntity.class);
        CredencialEntity credencialEntityResp = credencialRepository.save(credencialEntity);
        return modelMapper.map(credencialEntityResp, Credencial.class);
    }

    @Override
    public Credencial encontrarCredencial(String ci, String pass) {
        CredencialEntity credEnty = credencialRepository.encontrarCredencial(ci, pass);
        return modelMapper.map(credEnty, Credencial.class);
    }

    @Override
    public Credencial encontrarCredencialPorUsuarioId(Long idUsuario) {
        CredencialEntity credencialEntity = credencialRepository
                .encontrarCredencialPorUsuarioId(idUsuario);
        return (credencialEntity != null)?
                modelMapper.map(credencialEntity, Credencial.class):
                null;
    }
}
