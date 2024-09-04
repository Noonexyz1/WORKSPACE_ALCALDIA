package com.prototipo.infrastructure.rest.controller;


import com.prototipo.infrastructure.rest.request.CredencialRequest;
import com.prototipo.infrastructure.rest.response.DashboardConfigResponse;
import com.prototipo.infrastructure.rest.response.RolResponse;
import com.prototipo.infrastructure.rest.response.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "*", maxAge = 86400)
//@Validated
@RestController
@RequestMapping(path = "/dologin")
public class LoginController {

    @PostMapping(path = {""}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioResponse> iniciarSesion(@RequestBody CredencialRequest request){
        RolResponse rol = RolResponse.builder()
                .id(23L)
                .nombreRol("Administrador")
                .build();

        UsuarioResponse jefe = UsuarioResponse.builder()
                .id(23L)
                .nombres("Juanito")
                .apellidos("Hamburguesa")
                .build();

        //Mediante el ROl, un rol tiene varios componentes para verlos
        //uno de ellos es el admin-option
        List<DashboardConfigResponse> dashConfigList = new ArrayList<>();
        dashConfigList.add(DashboardConfigResponse.builder()
                        .id(1L)
                        .nombreComponente("admin-option")
                        .datosCompononente("todas mis opciones")
                        .build());

        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .id(23L)
                .nombres("Pablito")
                .apellidos("De la Roble")
                .fk_responsable(jefe)
                .fk_rol(rol)
                .listDashConfig(dashConfigList)
                .build();

        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }
}
