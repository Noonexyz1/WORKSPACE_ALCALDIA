package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioUnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InicioSesionImpl implements InicioSesionAbstract {

    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;

    @Override
    public UsuarioDto iniciarSesionAbstract(String correo, String pass) {
        Object[] usuarioDto = (Object[]) usuarioUnidadRepository
                .findUsuarioByCredencial(correo, pass)[0];

        UsuarioDto usuario = UsuarioDto.builder()
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
}
