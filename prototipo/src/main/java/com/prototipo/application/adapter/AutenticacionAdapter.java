package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.CredencialAbstract;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.application.useCase.AutenticacionService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Usuario;

public class AutenticacionAdapter implements AutenticacionService {

    private CredencialAbstract credencialAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private UsuarioAbastract usuarioAbastract;

    public AutenticacionAdapter(
            CredencialAbstract credencialAbstract,
            MapperApplicationAbstract mapperApplicationAbstract,
            UsuarioAbastract usuarioAbastract) {

        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.credencialAbstract = credencialAbstract;
        this.usuarioAbastract = usuarioAbastract;
    }

    @Override
    public Usuario iniciarSesion(String correo, String pass) {
        UsuarioDto usuarioDto = usuarioAbastract
                .iniciarSesionAbstract(correo, pass);
        return mapperApplicationAbstract
                .mapearAbstract(usuarioDto, Usuario.class);
    }

    @Override
    public void cambiarPass(Credencial credencial, String newPass) {
        String ci = credencial.getCi();
        String pass = credencial.getPass();
        CredencialDto credencialDto = credencialAbstract.encontrarCredencial(ci, pass);
        credencialDto.setPass(newPass);
        credencialAbstract.guardarCredencialAbstract(credencialDto);
    }

    @Override
    public void cerrarSesion() {
        //TODO cerrar sesion
    }
}
