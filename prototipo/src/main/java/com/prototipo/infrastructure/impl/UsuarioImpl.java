package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioRepository;
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
    private ModelMapper modelMapper;

    @Override
    public UsuarioDto findUsuarioPorIdAbastract(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    @Override
    public UsuarioDto guardarUsuarioAbastract(UsuarioDto usuarioDto) {
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        Usuario userRespo = usuarioRepository.save(usuario);
        UsuarioDto usuarioDtoResp = modelMapper.map(userRespo, UsuarioDto.class);
        return usuarioDtoResp;
    }

    @Override
    public List<UsuarioDto> listaDeUsuariosAbsDef(Long page, Long size) {
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        return usuarioRepository.findAll(pageable).stream()
                .map(x -> modelMapper.map(x, UsuarioDto.class))
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
}
