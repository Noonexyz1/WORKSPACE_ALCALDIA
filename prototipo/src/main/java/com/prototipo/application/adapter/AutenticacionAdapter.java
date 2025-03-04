package com.prototipo.application.adapter;

import com.prototipo.application.port.out.CredencialAbstract;
import com.prototipo.application.port.out.UsuarioAbastract;
import com.prototipo.application.port.in.AutenticacionService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Usuario;

public class AutenticacionAdapter implements AutenticacionService {

    private CredencialAbstract credencialAbstract;
    private UsuarioAbastract usuarioAbastract;

    public AutenticacionAdapter(
            CredencialAbstract credencialAbstract,
            UsuarioAbastract usuarioAbastract) {

        this.credencialAbstract = credencialAbstract;
        this.usuarioAbastract = usuarioAbastract;
    }

    @Override
    public Usuario iniciarSesion(String correo, String pass) {
        return usuarioAbastract.iniciarSesionAbstract(correo, pass);
    }

    @Override
    public void cambiarPass(Credencial credencial, String newPass) {
        String ci = credencial.getCi();
        String pass = credencial.getPass();
        Credencial credencialDto = credencialAbstract.encontrarCredencial(ci, pass);
        credencialDto.setPass(newPass);
        credencialAbstract.guardarCredencialAbstract(credencialDto);
    }

    @Override
    public void cerrarSesion() {
        //TODO cerrar sesion
    }
}
