package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.UsuarioAbastract;
import com.prototipo.domain.model.Usuario;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioEntity;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioRepository;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioUnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioImpl implements UsuarioAbastract {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Usuario iniciarSesionAbstract(String ci) {
        Object[] usuarioDto = (Object[]) usuarioUnidadRepository
                .findUsuarioByCredencial(ci)[0];

        Usuario usuario = Usuario.builder()
                .id((Long)usuarioDto[0])
                .nombres((String)usuarioDto[1])
                .paterno((String)usuarioDto[2])
                .materno((String)usuarioDto[3])
                .ci((String)usuarioDto[4])
                .correo((String)usuarioDto[5])
                .nombreRol((String)usuarioDto[6])
                .nombreUnidad((String)usuarioDto[7])
                .nombreCargo((String)usuarioDto[8])
                .build();
        return usuario;
    }

    @Override
    public Usuario guardarUsuarioAbastract(Usuario usuario) {
        UsuarioEntity usuarioEntity = modelMapper
                .map(usuario, UsuarioEntity.class);
        UsuarioEntity userRespo = usuarioRepository
                .save(usuarioEntity);
        return modelMapper.map(userRespo, Usuario.class);
    }
}
