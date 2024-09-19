package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.Usuario;

public class UsuarioAdapter implements UsuarioService {

    private UsuarioAbastract usuarioAbastract;

    public UsuarioAdapter(UsuarioAbastract usuarioAbastract){
        this.usuarioAbastract = usuarioAbastract;
    }

    @Override
    public Usuario findUsuarioPorIdService(Long idUnidad) {
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idUnidad);

        Usuario usuario = Usuario.builder()
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
