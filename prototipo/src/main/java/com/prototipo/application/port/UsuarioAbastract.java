package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

public interface UsuarioAbastract {
    PaginableOut<UsuarioUnidadDto> listaDeUsuariosAbsDef(PaginableIn paginableIn);
    UsuarioDto guardarUsuarioAbastract(UsuarioDto usuarioDto);
    UsuarioUnidadDto findUsuarioUnidadPorIdUserAbastract(Long id);
}
