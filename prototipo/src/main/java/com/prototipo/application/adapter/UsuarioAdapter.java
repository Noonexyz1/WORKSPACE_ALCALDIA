package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.Usuario;
import com.prototipo.domain.model.UsuarioUnidad;

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
    public Usuario findUsuarioPorIdService(Long idUnidad) {
        UsuarioDto usuarioDto = usuarioAbastract.findUsuarioPorIdAbastract(idUnidad);
        return mapperApplicationAbstract.mapearAbstract(usuarioDto, Usuario.class);
    }

    @Override
    public List<UsuarioUnidad> listaDeUsuariosServiceDef(Long page, Long size) {
        return usuarioAbastract.listaDeUsuariosAbsDef(page, size).stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, UsuarioUnidad.class))
                .toList();
    }

    @Override
    public List<Usuario> listaDeUsuariosServiceAsc(Long page, Long size, String byColumName) {
        return usuarioAbastract.listaDeUsuariosAbsAsc(page, size, byColumName).stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Usuario.class))
                .toList();
    }

    @Override
    public List<Usuario> listaDeUsuariosServiceDesc(Long page, Long size, String byColumName) {
        return usuarioAbastract.listaDeUsuariosAbsDesc(page, size, byColumName).stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Usuario.class))
                .toList();
    }
}
