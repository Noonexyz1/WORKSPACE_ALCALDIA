package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.InicioSesionService;
import com.prototipo.domain.model.CredencialDomain;
import com.prototipo.domain.model.DashboardConfigDomain;
import com.prototipo.domain.model.UsuarioDomain;
import com.prototipo.infrastructure.rest.request.CredencialRequest;
import com.prototipo.infrastructure.rest.response.UsuarioResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 86400)
//@Validated
@RestController
@RequestMapping(path = "/dologin")
public class LoginController {

    @Autowired
    private InicioSesionService inicioSesionService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = {""}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioResponse> iniciarSesion(@RequestBody CredencialRequest request){

        /*Estoy usando el modelo Credencial del paquete Dominio directamente
        con la escusa de que la Arquitectura Hexagonal se componete de una sola frontera,
        Dominio y Applicacion como uno solo y la infraestrucura como Uno,
        En total dos capas, por lo tanto, desde infraestructura puedo conocer los detalles de
        Infraestrucutura y Dominio en la parte de las importaciones de paquetes*/
        CredencialDomain credencialDomain = modelMapper.map(request, CredencialDomain.class);
        UsuarioDomain usuario = inicioSesionService.iniciarSesionService(credencialDomain);
        String configuracion = inicioSesionService.configuracionDeUsuarioService(usuario.getFkRol().getId());

        UsuarioResponse usuarioResponse = modelMapper.map(usuario, UsuarioResponse.class);
        usuarioResponse.setNombreRol(usuario.getFkRol().getNombreRol());
        usuarioResponse.setDashConfig(configuracion);

        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }
}
