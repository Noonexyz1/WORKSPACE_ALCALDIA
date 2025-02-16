package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

import java.util.List;

public interface UsuarioAbastract {
    UsuarioDto findUsuarioPorIdAbastract(Long idUsuario);
    PaginableOut<UsuarioUnidadDto> listaDeUsuariosAbsDef(PaginableIn paginableIn);
    List<UsuarioDto> listaDeUsuariosAbsAsc(Long page, Long size, String byColumName);
    List<UsuarioDto> listaDeUsuariosAbsDesc(Long page, Long size, String byColumName);
    UsuarioDto guardarUsuarioAbastract(UsuarioDto usuarioDto);
    UsuarioUnidadDto findUsuarioUnidadPorIdUserAbastract(Long id);
}
