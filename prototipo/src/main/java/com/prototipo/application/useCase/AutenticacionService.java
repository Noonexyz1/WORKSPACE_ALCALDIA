package com.prototipo.application.useCase;

import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Usuario;

public interface AutenticacionService {
    Usuario iniciarSesion(String correo, String pass);
    void cerrarSesion();
    void cambiarPass(Credencial credencial, String newPass);
}
