package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.port.UsuarioUnidadAbstract;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioUnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<UsuarioUnidadDto> encontrarUsuariosUnidadByUsuarioId(Long idUsuario) {
        List<UsuarioUnidadEntity> usuarioUnidad = usuarioUnidadRepository
                .encontrarUsuariosUnidadPorUsuarioId(idUsuario);
        return usuarioUnidad.stream()
                .map(x -> mapper.map(x, UsuarioUnidadDto.class))
                .toList();
    }

    @Override
    public UsuarioUnidadDto encontarUsuarioUnidadId(Long idUsuarioUnidad) {
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository
                .findById(idUsuarioUnidad)
                .orElse(null);
        return mapper.map(usuarioUnidad, UsuarioUnidadDto.class);
    }
}
