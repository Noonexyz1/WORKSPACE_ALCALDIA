package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.Usuario;
import com.prototipo.domain.model.UsuarioUnidad;

import java.util.List;

public class UsuarioAdapter implements UsuarioService {

    private UsuarioAbastract usuarioAbastract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public UsuarioAdapter(
            UsuarioAbastract usuarioAbastract,
            MapperApplicationAbstract mapperApplicationAbstract){

        this.usuarioAbastract = usuarioAbastract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public PaginableOut<UsuarioUnidad> listaDeUsuariosServiceDef(PaginableIn paginableIn) {
        PaginableOut<UsuarioUnidadDto> paginableOut = usuarioAbastract
                .listaDeUsuariosAbsDef(paginableIn);

        PaginableOut<UsuarioUnidad> paginableResponse = PaginableOut
                .<UsuarioUnidad>builder()
                .content(
                        paginableOut.getContent()
                        .stream()
                        .map(x -> mapperApplicationAbstract
                                .mapearAbstract(x, UsuarioUnidad.class)
                        )
                        .toList()
                )
                .totalPages(paginableOut.getTotalPages())
                .totalElements(paginableOut.getTotalElements())
                .build();

        return paginableResponse;
    }

    @Override
    public UsuarioUnidad findUsuarioUnidadByIdUSer(Long id) {
        UsuarioUnidadDto usuarioUnidadDto = usuarioAbastract
                .findUsuarioUnidadPorIdUserAbastract(id);
        return mapperApplicationAbstract
                .mapearAbstract(usuarioUnidadDto, UsuarioUnidad.class);
    }
}
