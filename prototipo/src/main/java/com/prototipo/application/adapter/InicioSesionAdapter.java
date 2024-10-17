package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.port.CredencialAbstract;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.application.useCase.InicioSesionService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Usuario;
import com.prototipo.domain.model.UsuarioUnidad;

public class InicioSesionAdapter implements InicioSesionService {

    private InicioSesionAbstract inicioSesionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private CredencialAbstract credencialAbstract;

    public InicioSesionAdapter(InicioSesionAbstract inicioSesionAbstract,
                               MapperApplicationAbstract mapperApplicationAbstract){

        this.inicioSesionAbstract = inicioSesionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public UsuarioUnidad iniciarSesionService(String correo, String pass) {
        UsuarioUnidadDto usuarioUnidadDto = inicioSesionAbstract.iniciarSesionAbstract(correo, pass);
        return mapperApplicationAbstract.mapearAbstract(usuarioUnidadDto, UsuarioUnidad.class);
    }

    @Override
    public String rolDeUsuarioService() {
        //TODO, ???????
        return null;
    }
}
