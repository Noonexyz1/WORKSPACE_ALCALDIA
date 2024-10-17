package com.prototipo.application.port;

import com.prototipo.application.modelDto.CredencialDto;

public interface CredencialAbstract {
    CredencialDto guardarCredencialAbstract(CredencialDto nuevaCred);
    CredencialDto encontrarCredencial(String correo, String pass);
    CredencialDto encontrarCredencialPorUsuarioId(Long idUsuario);
}
