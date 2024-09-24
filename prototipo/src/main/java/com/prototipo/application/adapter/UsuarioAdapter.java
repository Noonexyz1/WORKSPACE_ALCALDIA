package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.UsuarioDomain;

public class UsuarioAdapter implements UsuarioService {

    private UsuarioAbastract usuarioAbastract;
    //TODO hacer los mapeos

    public UsuarioAdapter(UsuarioAbastract usuarioAbastract){
        this.usuarioAbastract = usuarioAbastract;
    }

    @Override
    public UsuarioDomain findUsuarioPorIdService(Long idUnidad) {
        //TODO
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idUnidad);

        UsuarioDomain usuario = UsuarioDomain.builder()
                .id(usuarioDto.getId())
                .nombres(usuarioDto.getNombres())
                .apellidos(usuarioDto.getApellidos())
                //.responsable()
                //.credencial()
                //.rol()
                .build();

        return usuario;
    }
}
