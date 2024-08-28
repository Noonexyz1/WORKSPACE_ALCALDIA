package com.prototipo.infrastructure.rest;

import com.prototipo.infrastructure.rest.request.CredencialResq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @PostMapping
    public void iniciarSesion(CredencialResq credencialResq) {

    }

}
