package com.prototipo.application.useCase;

import com.prototipo.domain.model.UsuarioUnidad;

public interface InicioSesionService {
    UsuarioUnidad iniciarSesionService(String correo, String pass);
    String rolDeUsuarioService();
}
