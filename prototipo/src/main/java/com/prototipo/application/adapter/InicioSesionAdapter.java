package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.application.useCase.InicioSesionService;
import com.prototipo.domain.model.Usuario;

public class InicioSesionAdapter implements InicioSesionService {

    private InicioSesionAbstract inicioSesionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public InicioSesionAdapter(
            InicioSesionAbstract inicioSesionAbstract,
            MapperApplicationAbstract mapperApplicationAbstract){

        this.inicioSesionAbstract = inicioSesionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public Usuario iniciarSesionService(String correo, String pass) {
        UsuarioDto usuarioDto = inicioSesionAbstract
                .iniciarSesionAbstract(correo, pass);
        return mapperApplicationAbstract
                .mapearAbstract(usuarioDto, Usuario.class);
    }
}
