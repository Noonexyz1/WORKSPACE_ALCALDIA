package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.port.UsuarioUnidadAbstract;
import com.prototipo.domain.model.UsuarioUnidad;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioUnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioUnidadImpl implements UsuarioUnidadAbstract {

    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public UsuarioUnidadDto guardarUsuarioUnidad(UsuarioUnidadDto usuarioUnidadDto) {
        UsuarioUnidadEntity userUni = mapper.map(usuarioUnidadDto, UsuarioUnidadEntity.class);
        UsuarioUnidadEntity userUniResp = usuarioUnidadRepository.save(userUni);
        return mapper.map(userUniResp, UsuarioUnidadDto.class);
    }

    @Override
    public UsuarioUnidadDto encontrarUsuarioUnidadByUsuarioId(Long idUsuario) {
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository
                .encontrarUsuarioUnidadPorUsuarioId(idUsuario);
        return mapper.map(usuarioUnidad, UsuarioUnidadDto.class);
    }
}
