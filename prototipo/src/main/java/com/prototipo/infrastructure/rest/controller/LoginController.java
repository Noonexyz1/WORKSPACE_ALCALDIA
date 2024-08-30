package com.prototipo.infrastructure.rest.controller;


import com.prototipo.infrastructure.rest.request.CredencialRequest;
import com.prototipo.infrastructure.rest.response.CredencialResqonse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", maxAge = 86400)
//@Validated
@RestController
@RequestMapping(path = "/dologin")
public class LoginController {

    @PostMapping(path = {""}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CredencialResqonse> iniciarSesion(@RequestBody CredencialRequest request){
        CredencialResqonse response = CredencialResqonse.builder()
                .id(23L)
                .correo("asdfasdf@gmail.com")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
