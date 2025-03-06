package com.prototipo.application.port.out;

import com.prototipo.domain.model.Credencial;

public interface CredencialAbstract {
    Credencial guardarCredencialAbstract(Credencial nuevaCred);
    Credencial encontrarCredencial(String correo, String pass);
    Credencial encontrarCredencialPorUsuarioId(Long idUsuario);

    Credencial encontrarCredencialByCi(String ci);
}
