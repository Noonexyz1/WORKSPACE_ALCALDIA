package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioImpl implements UsuarioAbastract {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto findUsuarioPorIdAbastract(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();

        UsuarioDto usuarioDto = UsuarioDto.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                //.responsable()
                //.credencial()
                //.rol()
                .build();
        return usuarioDto;
    }
}
