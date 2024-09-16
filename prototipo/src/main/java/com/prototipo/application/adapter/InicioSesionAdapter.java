package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.application.useCase.InicioSesionService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Usuario;

import java.util.List;

public class InicioSesionAdapter implements InicioSesionService {

    private InicioSesionAbstract inicioSesionAbstract;

    public InicioSesionAdapter(InicioSesionAbstract inicioSesionAbstract){
        this.inicioSesionAbstract = inicioSesionAbstract;
    }

    @Override
    public Usuario iniciarSesionService(Credencial credencial) {
        //Mapeamos de Credencial a CredencialDto
        CredencialDto credencialDto = CredencialDto.builder()
                .id(credencial.getId())
                .nombreUser(credencial.getNombreUser())
                .pass(credencial.getPass())
                .build();

        //Mapeamos de Para devolver el usuario para cumplir con el dominio
        UsuarioDto usuarioDto = inicioSesionAbstract.iniciarSesionAbstract(credencialDto);

        return Usuario.builder()
                .id(usuarioDto.getId())
                .nombres(usuarioDto.getNombres())
                .apellidos(usuarioDto.getApellidos())
                .build();
    }

    @Override
    public String rolDeUsuarioService() {
        return inicioSesionAbstract.rolDeUsuarioAbstract();
    }

    @Override
    public List<String> configuracionDeUsuarioService() {
        return inicioSesionAbstract.configuracionDeUsuarioAbstract();
    }
}
