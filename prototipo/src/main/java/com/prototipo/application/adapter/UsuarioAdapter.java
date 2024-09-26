package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.UsuarioDomain;

import java.util.List;

public class UsuarioAdapter implements UsuarioService {

    private UsuarioAbastract usuarioAbastract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public UsuarioAdapter(UsuarioAbastract usuarioAbastract,
                          MapperApplicationAbstract mapperApplicationAbstract){

        this.usuarioAbastract = usuarioAbastract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public UsuarioDomain findUsuarioPorIdService(Long idUnidad) {
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idUnidad);
        return mapperApplicationAbstract.mapearAbstract(usuarioDto, UsuarioDomain.class);
    }

    @Override
    public List<UsuarioDomain> listaDeUsuarios() {
        return usuarioAbastract.listaDeUsuarios().stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, UsuarioDomain.class))
                .toList();
    }
}
