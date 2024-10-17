package com.prototipo.application.port;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.modelDto.UsuarioUnidadDto;

import java.util.List;

public interface UsuarioAbastract {
    UsuarioDto findUsuarioPorIdAbastract(Long idUsuario);
    UsuarioDto guardarUsuarioAbastract(UsuarioDto usuarioDto);
    List<UsuarioUnidadDto> listaDeUsuariosAbsDef(Long page, Long size);
    List<UsuarioDto> listaDeUsuariosAbsAsc(Long page, Long size, String byColumName);
    List<UsuarioDto> listaDeUsuariosAbsDesc(Long page, Long size, String byColumName);
    UsuarioDto buscarUsuarioPorEmail(String email);
}
