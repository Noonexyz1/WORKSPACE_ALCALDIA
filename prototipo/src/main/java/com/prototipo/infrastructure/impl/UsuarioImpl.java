package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UsuarioDto> listaDeUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(x -> modelMapper.map(x, UsuarioDto.class))
                .toList();
    }
}
