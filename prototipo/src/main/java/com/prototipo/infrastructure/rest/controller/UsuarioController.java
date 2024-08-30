package com.prototipo.infrastructure.rest.controller;

import com.prototipo.infrastructure.rest.request.CredencialResqonse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UsuarioController {

    @PostMapping
    public void iniciarSesion(CredencialResqonse credencialResq) {

    }

}
