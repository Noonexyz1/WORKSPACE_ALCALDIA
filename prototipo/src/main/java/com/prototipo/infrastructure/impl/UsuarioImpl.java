package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioEntity;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioRepository;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioUnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioImpl implements UsuarioAbastract {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDto findUsuarioPorIdAbastract(Long idUsuario) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario).orElseThrow();
        return modelMapper.map(usuarioEntity, UsuarioDto.class);
    }

    @Override
    public UsuarioDto guardarUsuarioAbastract(UsuarioDto usuarioDto) {
        UsuarioEntity usuarioEntity = modelMapper.map(usuarioDto, UsuarioEntity.class);
        UsuarioEntity userRespo = usuarioRepository.save(usuarioEntity);
        UsuarioDto usuarioDtoResp = modelMapper.map(userRespo, UsuarioDto.class);
        return usuarioDtoResp;
    }

    @Override
    public List<UsuarioUnidadDto> listaDeUsuariosAbsDef(Long page, Long size) {
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        return usuarioUnidadRepository.findAll(pageable).stream()
                .map(x -> modelMapper.map(x, UsuarioUnidadDto.class))
                .toList();
    }

    @Override
    public List<UsuarioDto> listaDeUsuariosAbsAsc(Long page, Long size, String byColumName) {
        Sort sort = Sort.by(Sort.Direction.ASC, byColumName);
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue(), sort);
        return usuarioRepository.findAll(pageable).stream()
                .map(x -> modelMapper.map(x, UsuarioDto.class))
                .toList();
    }

    @Override
    public List<UsuarioDto> listaDeUsuariosAbsDesc(Long page, Long size, String byColumName) {
        Sort sort = Sort.by(Sort.Direction.DESC, byColumName);
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue(), sort);
        return usuarioRepository.findAll(pageable).stream()
                .map(x -> modelMapper.map(x, UsuarioDto.class))
                .toList();
    }

    @Override
    public UsuarioDto buscarUsuarioPorEmail(String email) {
        UsuarioEntity usuarioResp = usuarioRepository.encontrarUsuarioPorEmail(email);
        return (usuarioResp != null)? modelMapper.map(usuarioResp, UsuarioDto.class): null;
    }
}
