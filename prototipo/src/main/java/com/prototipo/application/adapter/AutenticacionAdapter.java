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
    public Usuario iniciarSesion(String ci) {
        return usuarioAbastract.iniciarSesionAbstract(ci);
    }

    @Override
    public void cambiarPass(Credencial credencial, String newPass) {
        String ci = credencial.getCi();

        Credencial credencialDto = credencialAbstract.encontrarCredencialByCi(ci);
        credencialDto.setPass(newPass);

        credencialAbstract.guardarCredencialAbstract(credencialDto);
    }

    @Override
    public void cerrarSesion() {
        //Cerramos sesion por infraestrucutura
    }
}
