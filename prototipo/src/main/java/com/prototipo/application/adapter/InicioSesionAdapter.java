package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.modelDto.DashboardConfigDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.application.useCase.InicioSesionService;
import com.prototipo.domain.model.CredencialDomain;
import com.prototipo.domain.model.UsuarioDomain;

import java.util.List;

public class InicioSesionAdapter implements InicioSesionService {

    private InicioSesionAbstract inicioSesionAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public InicioSesionAdapter(InicioSesionAbstract inicioSesionAbstract,
                               MapperApplicationAbstract mapperApplicationAbstract){

        this.inicioSesionAbstract = inicioSesionAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }

    @Override
    public UsuarioDomain iniciarSesionService(CredencialDomain credencial) {
        //Mapeamos de Credencial a CredencialDto
        CredencialDto credencialDto = mapperApplicationAbstract.mapearAbstract(credencial, CredencialDto.class);
        //Mapeamos de Para devolver el usuario para cumplir con el dominio
        UsuarioDto usuarioDto = inicioSesionAbstract.iniciarSesionAbstract(credencialDto);
        return mapperApplicationAbstract.mapearAbstract(usuarioDto, UsuarioDomain.class);
    }

    @Override
    public String configuracionDeUsuarioService(Long idRolUsuario) {
        return inicioSesionAbstract.configuracionDeUsuarioAbstract(idRolUsuario)
                .getNombreComponente();
    }

    @Override
    public String rolDeUsuarioService() {
        //TODO, ???????
        return null;
    }
}
